package home.maintenance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@ComponentScan(basePackages = {"home.maintenance.controller"})
@PropertySource("classpath:application.properties")
@Import({PersistenceConfig.class})
public class ApplicationConfig {}
