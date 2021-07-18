package com.liuli.redis.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

    @Bean
    public RedissonClient redisson(RedissonProperties redissonProperties){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" +redissonProperties.getHost() +":" + redissonProperties.getPort());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
