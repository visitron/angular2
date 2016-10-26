package home.maintenance.database.config;

import home.maintenance.database.service.DatabaseUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Buibi on 26.10.2016.
 */
@Configuration
public class DatabaseConfiguration {

    @Bean
    public DatabaseUpdater getDatabaseUpdater() {
        return new DatabaseUpdater();
    }
}
