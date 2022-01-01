package com.example.project.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "bing")
    public BingMapsConfig bingMapsConfig() {
        return new BingMapsConfig();
    }
}
