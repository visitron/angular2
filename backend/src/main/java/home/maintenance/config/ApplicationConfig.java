package home.maintenance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@Import({PersistenceConfig.class,
        SecurityConfig.class,
        MethodSecurityConfig.class,
        WebMvcConfig.class
})
@ComponentScan(basePackages = {"home.maintenance.controller", "home.maintenance.service"})
@EnableAspectJAutoProxy
@EnableScheduling
public class ApplicationConfig {}