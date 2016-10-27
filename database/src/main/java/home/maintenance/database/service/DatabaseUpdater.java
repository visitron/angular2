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
        List<Delta> appliedScripts = getListOfAppliedScripts();
        appliedScripts.forEach(System.out::println);

        scripts.clear();

        try (Connection connection = dataSource.getConnection()) {
            scripts.forEach((script) -> {
                try {
                    ScriptUtils.executeSqlScript(connection, script);
                } catch (ScriptException e) {
                    logScriptApplyResult(new Delta(script.getFilename(), new Date(), "SUCCESS", null));
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
        ClassPathResource resource = new ClassPathResource("classpath:script/ddl/001-create_tables.sql");
        List<ClassPathResource> result = new ArrayList<>();
        result.add(resource);
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
