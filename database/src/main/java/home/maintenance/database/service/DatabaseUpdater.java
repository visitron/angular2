package home.maintenance.database.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void run() {

        List<ClassPathResource> scripts = getScripts();
        scripts.sort((o1, o2) -> o1.getFilename().compareTo(o2.getFilename()));

        final List<String> appliedScripts = getListOfAppliedScripts().stream().map(delta -> delta.scriptName)
                .collect(Collectors.toList());

        try (Connection connection = dataSource.getConnection()) {
            scripts.forEach((script) -> {
                try {
                    if (appliedScripts.contains(script.getFilename())) {
                        LOG.info(script.getFilename() + " is [SKIPPED]");
                    } else {
                        ScriptUtils.executeSqlScript(connection, script);
                        logScriptApplyResult(new Delta(script.getFilename(), new Date(), "SUCCESS", null));
                        LOG.info(script.getFilename() + " is [APPLIED]");
                    }
                } catch (ScriptException e) {
                    logScriptApplyResult(new Delta(script.getFilename(), new Date(), "FAILURE", e.getMessage()));
                    LOG.error(script.getFilename() + " is [FAILED]", e);
                }
            });
        } catch (SQLException e) {
            LOG.error(e);
        }

    }

    private List<Delta> getListOfAppliedScripts() {
        List<Delta> deltas = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM DELTA", rs -> {
            deltas.add(new Delta(rs.getLong("ID"),
                    rs.getString("SCRIPT_NAME"),
                    rs.getDate("TS"),
                    rs.getString("STATUS"),
                    rs.getString("FAILURE")
            ));
        });

        return deltas;
    }

    private void logScriptApplyResult(Delta script) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("scriptName", script.scriptName);
        parameterSource.addValue("ts", script.ts);
        parameterSource.addValue("status", script.status);
        parameterSource.addValue("failure", script.failure);

        jdbcTemplate.update("INSERT INTO DELTA (ID, SCRIPT_NAME, TS, STATUS, FAILURE) " +
                "VALUES (SEQ_DELTA.NEXTVAL, :scriptName, :ts, :status, :failure)", parameterSource);
    }

    private List<ClassPathResource> getScripts() {
        List<ClassPathResource> result = new ArrayList<>();
        result.add(new ClassPathResource("script/ddl/002-create_tables.sql"));
        result.add(new ClassPathResource("script/ddl/001-create_tables.sql"));
        return result;
    }

    private class Delta {
        long id;
        String scriptName;
        Date ts;
        String status;
        String failure;

        Delta() {}

        Delta(String scriptName, Date ts, String status, String failure) {
            this.scriptName = scriptName;
            this.ts = ts;
            this.status = status;
            this.failure = failure;
        }

        Delta(long id, String scriptName, Date ts, String status, String failure) {
            this.id = id;
            this.scriptName = scriptName;
            this.ts = ts;
            this.status = status;
            this.failure = failure;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s", ts, status, scriptName);
        }
    }

}
