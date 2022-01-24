package com.liuli.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {

    private String name;

    private String age;

}
