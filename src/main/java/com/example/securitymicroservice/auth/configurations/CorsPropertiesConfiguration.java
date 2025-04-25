package com.example.securitymicroservice.auth.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsPropertiesConfiguration {
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowCredentials;
    private List<String> exposedHeaders;
}
