package home.maintenance.database.config;

import home.maintenance.database.service.DatabaseUpdater;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by Buibi on 26.10.2016.
 */
@Configuration
@PropertySource("classpath:/database.oracle.properties")
public class DatabaseConfiguration {

    @Bean
    public DatabaseUpdater getDatabaseUpdater() throws NoSuchAlgorithmException {
        return new DatabaseUpdater();
    }

    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.user}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public DataSource getDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);

        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource) throws SQLException {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
