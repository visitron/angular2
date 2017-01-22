package home.maintenance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Buibi on 22.01.2017.
 */
@Configuration
public class StaticResourcesConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.resources.static-locations}")
    private String staticResourcesLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations(staticResourcesLocation);
    }
}
