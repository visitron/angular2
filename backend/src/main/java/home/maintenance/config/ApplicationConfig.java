package home.maintenance.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@Import({PersistenceConfig.class, WebMvcConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = {"home.maintenance.controller", "home.maintenance.service"})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {}
