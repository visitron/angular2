package home.maintenance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by vsoshyn on 28/10/2016.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JndiObjectFactoryBean getJndiObjectFactoryBean() {
        return new JndiObjectFactoryBean();
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = getJndiObjectFactoryBean();
//        jndiObjectFactoryBean.setJndiEnvironment(environment);
        jndiObjectFactoryBean.setJndiName(environment.getProperty("database.jndi"));
        jndiObjectFactoryBean.afterPropertiesSet();
        return (DataSource) jndiObjectFactoryBean.getObject();
    }


}
