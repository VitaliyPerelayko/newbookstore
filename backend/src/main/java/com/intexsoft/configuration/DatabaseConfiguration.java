package com.intexsoft.configuration;


import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class for database module of project
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@EnableJpaRepositories(basePackages = {
        "com.intexsoft.dao.repository"
})
public class DatabaseConfiguration {

    @Value("${connection.driver_class}")
    private String driverClass;

    @Value("${connection.url}")
    private String url;

    @Value("${dialect}")
    private String dialect;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;


    /**
     * create Bean flyway
     *
     * @return FlyWay
     */
    @Bean
    public int flyway(){
        return Flyway.configure().dataSource(dataSource()).load().migrate();
    }

    /**
     * Get info for connection to database
     *
     * @return configuration of database
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSourceConfig = new DriverManagerDataSource();
        dataSourceConfig.setDriverClassName(driverClass);
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        return dataSourceConfig;
    }



    /**
     * Set up a shared JPA EntityManagerFactory in a Spring application context
     *
     * @return EntityManagerFactory
     */
    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.intexsoft.dao.model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * TransactionManager is required for JPA access code
     * supporting this transaction management mechanism.
     * This transaction manager is appropriate for applications that use a single
     * JPA EntityManagerFactory for transactional data access.
     *
     * @param emf entityManagerFactory.
     * @return transactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.jdbc.batch_size", "5");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");

        return properties;
    }
}
