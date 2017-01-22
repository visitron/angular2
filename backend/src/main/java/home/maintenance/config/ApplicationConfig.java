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
@EnableWebMvc
@Import({PersistenceConfig.class, StaticResourcesConfig.class})
@ComponentScan(basePackages = {"home.maintenance.controller", "home.maintenance.service"})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {}
