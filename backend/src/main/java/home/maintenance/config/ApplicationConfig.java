package home.maintenance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@ComponentScan(basePackages = {"home.maintenance.controller", "home.maintenance.dao"})
@Import({PersistenceConfig.class})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {}
