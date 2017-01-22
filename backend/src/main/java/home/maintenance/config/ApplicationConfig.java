package home.maintenance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@Import({PersistenceConfig.class})
@ComponentScan(basePackages = {"home.maintenance.controller"})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {}
