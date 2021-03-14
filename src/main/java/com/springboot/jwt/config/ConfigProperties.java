package com.springboot.jwt.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwtdemo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperties {
    private String secretKey;
}
