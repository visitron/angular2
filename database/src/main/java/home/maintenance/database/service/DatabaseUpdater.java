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
import java.util.stream.Collectors;

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

        final boolean hasError = deltas.stream().anyMatch(delta -> delta.status == Status.FAILURE);
        if (hasError) {
            LOG.error("Some scripts have applying failures");
            deltas.stream().filter(delta -> delta.status == Status.FAILURE).forEach(LOG::error);
            throw new RuntimeException("Some scripts have applying failures");
        }

        final Map<String, String> appliedScripts = new HashMap<>();
        deltas.forEach(delta -> appliedScripts.put(delta.scriptName, delta.digest));

        final boolean[] scriptDigestMismatches = new boolean[1];
        scripts.forEach(script -> {
            try {
                String d0 = digest(script.getInputStream());
                String d1 = appliedScripts.get(script.getPath());
                if (d1 != null && !Objects.equals(d0, d1)) {
                    scriptDigestMismatches[0] = true;
                    LOG.error(String.format("Checksum mismatch: script[%s] - file[%s] - database[%s]", script.getPath(), d0, d1));
                }
            } catch (IOException e) {
                LOG.error(e);
                throw new RuntimeException(e);
            }
        });

        if (scriptDigestMismatches[0]) {
            throw new RuntimeException("Checksum mismatch");
        }

        try (Connection connection = dataSource.getConnection()) {

            if (deltas.isEmpty()) {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("script/init.sql"));
            }

            applyScripts(scripts, appliedScripts, connection);
            List<ClassPathResource> stageScripts = getScripts(ScriptLocation.STAGE);
            registerScripts(stageScripts);
            moveToRepo(stageScripts);
        } catch (SQLException | IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }

    }

    private void registerScripts(List<ClassPathResource> scripts) {
        for (ClassPathResource script : scripts) {
            try {
                logScriptApplyResult(new Delta(script.getPath().replace("stage", "script"), new Date(), Status.SUCCESS, null, digest(script.getInputStream())));
            } catch (IOException e) {
                logScriptApplyResult(new Delta(script.getPath().replace("stage", "script"), new Date(), Status.FAILURE, e.getMessage(), null));
                LOG.error(script.getPath() + " is [FAILED]", e);
                throw new RuntimeException(script.getPath() + " is [FAILED]", e);
            }
        }
    }

    private void moveToRepo(List<ClassPathResource> scripts) throws IOException {
        File targetFolder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String resources = targetFolder.getParentFile().getParentFile().getPath() + "/src/main/resources/";
        for (ClassPathResource script : scripts) {
            Files.move(Paths.get(resources + script.getPath()), Paths.get(resources + Type.resolveType(script.getPath()).getPath() + script.getFilename()));
        }
    }

    private void applyScripts(List<ClassPathResource> scripts, Map<String, String> appliedScripts, Connection connection) {
        for (ClassPathResource script : scripts) {
            try {
                if (appliedScripts.containsKey(script.getPath())) {
                    if (appliedScripts.get(script.getPath()) == null) {
                        updateAppliedScriptHash(script);
                        LOG.info(script.getPath() + " hash is [UPDATED]");
                    } else {
                        LOG.info(script.getPath() + " is [SKIPPED]");
                    }
                } else {
                    ScriptUtils.executeSqlScript(connection, script);
                    logScriptApplyResult(new Delta(script.getPath(), new Date(), Status.SUCCESS, null, digest(script.getInputStream())));
                }
            } catch (ScriptException | IOException e) {
                logScriptApplyResult(new Delta(script.getPath(), new Date(), Status.FAILURE, e.getMessage(), null));
                LOG.error(script.getPath() + " is [FAILED]", e);
                throw new RuntimeException(script.getPath() + " is [FAILED]", e);
            }
        }
    }

    private List<Delta> getListOfAppliedScripts() {
        List<Delta> deltas = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM DELTA", rs -> {
            deltas.add(new Delta(rs.getString("SCRIPT_NAME"),
                    rs.getDate("TS"),
                    Status.valueOf(rs.getString("STATUS")),
                    rs.getString("FAILURE"),
                    rs.getString("DIGEST")
            ));
        });

        return deltas;
    }

    private void logScriptApplyResult(Delta script) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("scriptName", script.scriptName);
        parameterSource.addValue("type", script.type.name());
        parameterSource.addValue("ts", script.ts);
        parameterSource.addValue("status", script.status.name());
        parameterSource.addValue("failure", script.failure);
        parameterSource.addValue("digest", script.digest);

        jdbcTemplate.update("INSERT INTO DELTA (SCRIPT_NAME, TYPE, TS, STATUS, FAILURE, DIGEST) " +
                "VALUES (:scriptName, :type, :ts, :status, :failure, :digest)", parameterSource);
    }

    private void updateAppliedScriptHash(ClassPathResource script) throws IOException {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("scriptName", script.getPath());
        parameterSource.addValue("digest", digest(script.getInputStream()));

        jdbcTemplate.update("UPDATE DELTA SET DIGEST = :digest WHERE SCRIPT_NAME = :scriptName", parameterSource);
    }

    private List<ClassPathResource> getScripts(ScriptLocation location) {
        List<ClassPathResource> result = new ArrayList<>();
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            if (location == ScriptLocation.REPO) {
                result.addAll(getScripts0(patternResolver, Type.DDL));
                result.addAll(getScripts0(patternResolver, Type.DML));
            } else if (location == ScriptLocation.STAGE) {
                result.addAll(getScripts0(patternResolver, Type.DDL_STAGE));
                result.addAll(getScripts0(patternResolver, Type.DML_STAGE));
            }
        } catch (IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<ClassPathResource> getScripts0(ResourcePatternResolver patternResolver, Type type) throws IOException {
        List<ClassPathResource> result = new ArrayList<>();
        String mask = type.getPath();
        Resource[] resources = patternResolver.getResources(mask + "*.sql");
        for (Resource resource : resources) {
            result.add(new ClassPathResource(mask + resource.getFilename()));
        }
        result.sort((o1, o2) -> o1.getFilename().compareTo(o2.getFilename()));
        return result;
    }

    private String digest(InputStream input) {
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
        Type type;
        Date ts;
        Status status;
        String failure;
        String digest;

        Delta(String scriptName, Date ts, Status status, String failure, String digest) {
            this.scriptName = scriptName;
            this.type = Type.resolveType(scriptName);
            this.ts = ts;
            this.status = status;
            this.failure = failure;
            this.digest = digest;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s", ts, status, scriptName);
        }
    }

    private enum Status {SUCCESS, FAILURE}
    private enum ScriptLocation {REPO, STAGE}

    private enum Type {
        DDL("script/ddl/"), DML("script/dml/"),
        DDL_STAGE("stage/ddl/"), DML_STAGE("stage/dml/"),
        UNKNOWN("UNKNOWN");

        private final String path;
        private Type(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public static Type resolveType(String scriptName) {
            Type type;
            if (scriptName.startsWith(DDL.getPath()) || scriptName.startsWith(DDL_STAGE.getPath())) {
                type = DDL;
            } else if (scriptName.startsWith(DML.getPath()) || scriptName.startsWith(DML_STAGE.getPath())) {
                type = DML;
            } else {
                type = UNKNOWN;
            }
            return type;
        }
    }

}
