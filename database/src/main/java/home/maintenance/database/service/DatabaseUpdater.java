package home.maintenance.database.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Buibi on 26.10.2016.
 */
@Component
public class DatabaseUpdater implements Runnable {

    private static final Logger LOG = Logger.getLogger(DatabaseUpdater.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    private final MessageDigest messageDigest;

    public DatabaseUpdater() throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance("MD5");
    }

    public void run() {
        List<ClassPathResource> scripts = getScripts(ScriptLocation.REPO);
        List<Delta> deltas;
        try {
            deltas = getListOfAppliedScripts();
        } catch (Exception e) {
            deltas = Collections.emptyList();
        }

        int currentId = deltas.isEmpty() ? 0 : deltas.stream().map(delta -> Integer.parseInt(delta.scriptName.substring(7, 10))).max((o1, o2) -> o1 - o2).get() + 1;

        final boolean hasError = deltas.stream().anyMatch(delta -> delta.status == Status.FAILURE);
        if (hasError) {
            LOG.error("Some scripts have applying failures");
            deltas.stream().filter(delta -> delta.status == Status.FAILURE).forEach(LOG::error);
            throw new RuntimeException("Some scripts have applying failures");
        }

        final Map<String, String> appliedScripts = new HashMap<>();
        deltas.forEach(delta -> appliedScripts.put(delta.scriptName, delta.checksum));

        final boolean[] scriptChecksumMismatches = new boolean[1];
        scripts.forEach(script -> {
            try {
                String d0 = checksum(script.getInputStream());
                String d1 = appliedScripts.get(script.getPath());
                if (d1 != null && !Objects.equals(d0, d1)) {
                    scriptChecksumMismatches[0] = true;
                    LOG.error(String.format("Checksum mismatch: script[%s] - file[%s] - database[%s]", script.getPath(), d0, d1));
                }
            } catch (IOException e) {
                LOG.error(e);
                throw new RuntimeException(e);
            }
        });

        if (scriptChecksumMismatches[0]) {
            throw new RuntimeException("Checksum mismatch");
        }

        try (Connection connection = dataSource.getConnection()) {
            currentId += applyScripts(scripts, appliedScripts, connection);
            List<ClassPathResource> stageScripts = getScripts(ScriptLocation.STAGE);

            Queue<Integer> ids = generateIds(currentId, stageScripts.size());
            registerScripts(stageScripts, ids);

            ids = generateIds(currentId, scripts.size());
            moveToRepo(stageScripts, ids);
        } catch (SQLException | IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }

    }

    private Queue<Integer> generateIds(int currentId, int amount) {
        Queue<Integer> ids = new PriorityQueue<>();
        for (int i = currentId; i < currentId + amount; i++) {
            ids.add(i);
        }
        return ids;
    }

    private String makeScriptName(String initialPath, Integer id) {
        String[] pathParts = initialPath.replace("stage", "script").split("/");
        return String.format("%s/%03d-%s", pathParts[0], id, pathParts[1].substring(2));
    }

    private void registerScripts(List<ClassPathResource> scripts, Queue<Integer> ids) {
        for (ClassPathResource script : scripts) {
            String scriptName = makeScriptName(script.getPath(), ids.poll());
            try {
                logScriptApplyResult(new Delta(scriptName, new Date(), Status.SUCCESS, null, checksum(script.getInputStream())));
            } catch (IOException e) {
                logScriptApplyResult(new Delta(scriptName, new Date(), Status.FAILURE, e.getMessage(), null));
                LOG.error(script.getPath() + " is [FAILED]", e);
                throw new RuntimeException(script.getPath() + " is [FAILED]", e);
            }
        }
    }

    private void moveToRepo(List<ClassPathResource> scripts, Queue<Integer> ids) throws IOException {
        File targetFolder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String resources = targetFolder.getParentFile().getParentFile().getPath() + "/src/main/resources/";
        for (ClassPathResource script : scripts) {
            Files.move(Paths.get(resources + script.getPath()), Paths.get(String.format("%s%03d-%s", resources + "script/", ids.poll(), script.getFilename().substring(2))));
        }
    }

    private int applyScripts(List<ClassPathResource> scripts, Map<String, String> appliedScripts, Connection connection) {
        int appliedScriptsCount = 0;
        for (ClassPathResource script : scripts) {
            try {
                if (appliedScripts.containsKey(script.getPath())) {
                    if (appliedScripts.get(script.getPath()) == null) {
                        updateAppliedScriptChecksum(script);
                        LOG.info(script.getPath() + " Checksum is [UPDATED]");
                    } else {
                        LOG.info(script.getPath() + " is [SKIPPED]");
                    }
                } else {
                    ScriptUtils.executeSqlScript(connection, script);
                    appliedScriptsCount++;
                    logScriptApplyResult(new Delta(script.getPath(), new Date(), Status.SUCCESS, null, checksum(script.getInputStream())));
                }
            } catch (ScriptException | IOException e) {
                logScriptApplyResult(new Delta(script.getPath(), new Date(), Status.FAILURE, e.getMessage(), null));
                LOG.error(script.getPath() + " is [FAILED]", e);
                throw new RuntimeException(script.getPath() + " is [FAILED]", e);
            }
        }
        return appliedScriptsCount;
    }

    private List<Delta> getListOfAppliedScripts() {
        List<Delta> deltas = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM DELTA", rs -> {
            deltas.add(new Delta(rs.getString("SCRIPT_NAME"),
                    rs.getDate("TS"),
                    Status.valueOf(rs.getString("STATUS")),
                    rs.getString("FAILURE"),
                    rs.getString("CHECKSUM")
            ));
        });

        return deltas;
    }

    private void logScriptApplyResult(Delta script) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("scriptName", script.scriptName);
        parameterSource.addValue("ts", script.ts);
        parameterSource.addValue("status", script.status.name());
        parameterSource.addValue("failure", script.failure);
        parameterSource.addValue("checksum", script.checksum);

        jdbcTemplate.update("INSERT INTO DELTA (SCRIPT_NAME, TS, STATUS, FAILURE, CHECKSUM) " +
                "VALUES (:scriptName, :ts, :status, :failure, :checksum)", parameterSource);
    }

    private void updateAppliedScriptChecksum(ClassPathResource script) throws IOException {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("scriptName", script.getPath());
        parameterSource.addValue("checksum", checksum(script.getInputStream()));

        jdbcTemplate.update("UPDATE DELTA SET CHECKSUM = :checksum WHERE SCRIPT_NAME = :scriptName", parameterSource);
    }

    private List<ClassPathResource> getScripts(ScriptLocation location) {
        List<ClassPathResource> result = new ArrayList<>();
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            result.addAll(getScripts0(patternResolver, location));
        } catch (IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<ClassPathResource> getScripts0(ResourcePatternResolver patternResolver, ScriptLocation location) throws IOException {
        List<ClassPathResource> result = new ArrayList<>();
        String mask = location.getPath();
        Resource[] resources = patternResolver.getResources(mask + "*.sql");
        for (Resource resource : resources) {
            result.add(new ClassPathResource(mask + resource.getFilename()));
        }
        result.sort((o1, o2) -> o1.getFilename().compareTo(o2.getFilename()));
        return result;
    }

    private String checksum(InputStream input) {
        messageDigest.reset();
        try (DigestInputStream dis = new DigestInputStream(input, messageDigest)) {
            while (dis.read() != -1) {}
        } catch (IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
        return DatatypeConverter.printHexBinary(messageDigest.digest());
    }

    private class Delta {
        String scriptName;
        Date ts;
        Status status;
        String failure;
        String checksum;

        Delta(String scriptName, Date ts, Status status, String failure, String checksum) {
            this.scriptName = scriptName;
            this.ts = ts;
            this.status = status;
            this.failure = failure;
            this.checksum = checksum;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s", ts, status, scriptName);
        }
    }

    private enum Status {SUCCESS, FAILURE}
    private enum ScriptLocation {
        REPO("script/"),
        STAGE("stage/");

        private final String path;
        ScriptLocation(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

    }

}
