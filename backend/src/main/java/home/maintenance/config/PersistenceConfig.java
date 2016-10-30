package home.maintenance.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.WebLogicJtaTransactionManager;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Buibi on 29.10.2016.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JndiObjectFactoryBean getJndiObjectFactoryBean() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName(environment.getProperty("database.jndi"));
        jndiObjectFactoryBean.afterPropertiesSet();
        return jndiObjectFactoryBean;
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws NamingException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("home.maintenance.model");
        entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource(@Autowired JndiObjectFactoryBean jndiObjectFactoryBean) throws NamingException {
        return (DataSource) jndiObjectFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new WebLogicJtaTransactionManager();
    }
}
