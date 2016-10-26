package home.maintenance.database.service;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Buibi on 26.10.2016.
 */
@Component
public class DatabaseUpdater implements Runnable {

    private static final Logger LOG = Logger.getLogger(DatabaseUpdater.class);

//    @Autowired
    private DataSource ds;

    public void run() {

        try {
            ds = new OracleDataSource();
//            Connection connection = ds.getConnection();
//            ScriptUtils.executeSqlScript(connection, new ClassPathResource("script/init.sql"));
            LOG.info("Passed");
        } catch (NullPointerException e) {
            LOG.error(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
