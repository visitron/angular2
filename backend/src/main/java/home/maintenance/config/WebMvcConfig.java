package home.maintenance.config;

import home.maintenance.service.ImageRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * Created by Buibi on 22.01.2017.
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${application.resources.static-locations}")
    private String staticResourcesLocation;
    @Value("${application.resources.static.drop-user-data}")
    private boolean dropUserData;

    @Autowired
    private ImageRepositoryManager imageRepository;
    @Autowired
    private Jackson2ObjectMapperBuilder builder;

    private List<HttpMessageConverter<?>> converters;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("file:/" + staticResourcesLocation);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        this.converters = converters;
        MappingJackson2HttpMessageConverter httpMessageConverter = mappingJackson2HttpMessageConverter();
        converters.add(httpMessageConverter);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(builder.build());
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(false, this.converters);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (dropUserData) {
            imageRepository.drop();
        }
    }

}
