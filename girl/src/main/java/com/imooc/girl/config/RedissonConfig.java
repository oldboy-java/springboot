package com.imooc.girl.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RedisConfig.class)
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedisConfig redisConfig) {
        // 1、创建配置
        Config cfg = new Config();
        // 使用单机模式
        cfg.useSingleServer().setAddress("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());

        // 2、 创建客户端
        RedissonClient redissonClient = Redisson.create(cfg);
        return redissonClient;
    }
}
