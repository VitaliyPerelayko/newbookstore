package com.intexsoft.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Base configuration of application
 */
@Configuration
@ComponentScan(basePackages = "com.intexsoft")
@Import({WebConfiguration.class, DatabaseConfiguration.class})
public class AppConfiguration {
}
