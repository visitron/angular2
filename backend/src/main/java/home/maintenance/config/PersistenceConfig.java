package home.maintenance.config;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.h2.Driver;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Buibi on 29.10.2016.
 */
@Configuration
//@ComponentScan(basePackages = "home.maintenance.dao")
//@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "home.maintenance.dao.common")
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean("entityManagerFactory")
    @Profile("h2")
    public EntityManagerFactory entityManagerFactoryH2(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = createCommonEMFBean(dataSource);
        bean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create-drop");
        bean.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean("entityManagerFactory")
    @Profile("oracle")
    public EntityManagerFactory entityManagerFactoryOracle(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = createCommonEMFBean(dataSource);
        bean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "validate");
        bean.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        bean.getJpaPropertyMap().put("hibernate.default_schema", "soshvla");

        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private LocalContainerEntityManagerFactoryBean createCommonEMFBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPersistenceProvider(new HibernatePersistenceProvider());
        bean.setPersistenceUnitName("home-maintenance");
        bean.setPackagesToScan("home.maintenance.model");

        bean.getJpaPropertyMap().put("hibernate.show_sql", "false");
        bean.getJpaPropertyMap().put("hibernate.format_sql", "true");
        return bean;
    }

    @Bean
    public EntityManager entityManager(@Autowired EntityManagerFactory entityManagerFactory) {
        SharedEntityManagerBean bean = new SharedEntityManagerBean();
        bean.setEntityManagerFactory(entityManagerFactory);
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    @Profile("h2")
    public DataSource dataSourceH2() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:h2:mem:soshvla;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("soshvla");
        return dataSource;
    }

    @Bean
    @Profile("oracle")
    public DataSource dataSourceOracle() throws SQLException {
        OracleConnectionPoolDataSource dataSource = new OracleConnectionPoolDataSource();
        dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/XE");
        dataSource.setUser("soshvla");
        dataSource.setPassword("12345678");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
