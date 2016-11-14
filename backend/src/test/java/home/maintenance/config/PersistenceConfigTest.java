package home.maintenance.config;

import org.h2.Driver;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
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

    @Profile("h2-ci")
    @Bean
    public DataSource h2DataSource() throws NamingException, SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:h2:mem:soshvla;DB_CLOSE_DELAY=-1");
        dataSource.setUsername(jdbcUser);
//        dataSource.setSchema("soshvla");

//        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

//    @Profile("oracle-ci")
//    @Bean
//    public DataSource oracleDataSource() throws NamingException, SQLException {
//        OracleDataSource dataSource = new OracleDataSource();
//        dataSource.setURL(jdbcUrl);
//        dataSource.setUser(jdbcUser);
//        dataSource.setPassword(jdbcPassword);
//        return dataSource;
//    }

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
        entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.show_sql", "true");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.format_sql", "true");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        entityManagerFactory.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        entityManagerFactory.getJpaPropertyMap().put("hibernate.default_schema", "soshvla");
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
    public PlatformTransactionManager txManager(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
