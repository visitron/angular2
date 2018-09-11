package home.maintenance.config;

import home.maintenance.service.ImageRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * Created by Buibi on 22.01.2017.
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

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

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springfox petstore API")
                .description("description")
                .contact(new Contact("name", "url", "email"))
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (dropUserData) {
            imageRepository.drop();
        }
    }

}
