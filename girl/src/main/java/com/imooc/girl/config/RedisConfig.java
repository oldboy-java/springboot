package com.imooc.girl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.redis")
@Data
@Configuration
public class RedisConfig {
    private String host;
    private int port;
    private int db;
}
