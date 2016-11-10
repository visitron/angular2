package home.maintenance.config;

import oracle.jdbc.pool.OracleDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Buibi on 09.11.2016.
 */
@Configuration
@ComponentScan(basePackages = "home.maintenance.dao")
@PropertySource("classpath:/database.oracle.properties")
@EnableTransactionManagement
public class PersistenceConfigTest {

    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.user}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(@Autowired DataSource dataSource) throws NamingException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("home.maintenance.model");
        entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactory.setPersistenceUnitName("home-maintenance");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.show_sql", "true");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.format_sql", "true");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.default_schema", "soshvla");
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public EntityManager entityManager(@Autowired EntityManagerFactory entityManagerFactory) {
        SharedEntityManagerBean bean = new SharedEntityManagerBean();
        bean.setEntityManagerFactory(entityManagerFactory);
        return bean.getObject();
    }

    @Bean
    public DataSource dataSource() throws NamingException, SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
