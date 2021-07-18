package com.liuli.redis.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonProperties {
    private String host;
    private String port;

}
