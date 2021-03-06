package home.maintenance.config;

import home.maintenance.service.CreatedByAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@Import({PersistenceConfig.class,
        SecurityConfig.class,
        MethodSecurityConfig.class,
        StateMachineConfig.class,
        WebMvcConfig.class
})
@ComponentScan(basePackages = {"home.maintenance.controller", "home.maintenance.service"})
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "createdByAware")
@EnableScheduling
public class ApplicationConfig {
    @Bean
    public CreatedByAware createdByAware() {
        return new CreatedByAware();
    }
}
