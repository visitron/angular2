package home.maintenance.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.WebLogicJtaTransactionManager;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Buibi on 29.10.2016.
 */
@Configuration
@ComponentScan(basePackages = "home.maintenance.dao")
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public EntityManagerFactory entityManagerFactory(@Autowired DataSource dataSource) throws NamingException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("home.maintenance.model");
        entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactory.setPersistenceUnitName("home-maintenance");
//        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.show_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.format_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//        entityManagerFactory.getJpaPropertyMap().put("javax.persistence.transactionType", "jta");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.transaction.manager_lookup_class", "org.hibernate.transaction.WeblogicTransactionManagerLookup");
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public EntityManager entityManager(@Autowired EntityManagerFactory entityManagerFactory) {
        SharedEntityManagerBean bean = new SharedEntityManagerBean();
        bean.setEntityManagerFactory(entityManagerFactory);
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName(environment.getProperty("database.jndi"));
        jndiObjectFactoryBean.afterPropertiesSet();
        return (DataSource) jndiObjectFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager txManager(@Autowired EntityManagerFactory entityManagerFactory) {
//        PlatformTransactionManager platformTransactionManager = new WebLogicJtaTransactionManager();
        PlatformTransactionManager platformTransactionManager = new JpaTransactionManager(entityManagerFactory);
        return platformTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
