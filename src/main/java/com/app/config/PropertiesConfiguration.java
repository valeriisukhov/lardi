package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
@Configuration
public class PropertiesConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocation(new FileSystemResource(System.getProperty("lardi.conf")));
        return propertySourcesPlaceholderConfigurer;
    }
}
