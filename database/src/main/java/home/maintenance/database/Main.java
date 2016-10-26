package home.maintenance.database;

import home.maintenance.database.config.DatabaseConfiguration;
import home.maintenance.database.service.DatabaseUpdater;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Buibi on 26.10.2016.
 */
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfiguration.class);
        DatabaseUpdater updater = context.getBean(DatabaseUpdater.class);
        updater.run();

    }
}
