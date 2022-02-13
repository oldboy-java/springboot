package com.glodon.limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisConfiguration {

    /**
     * 定义加载Lua脚本对象DefaultRedisScript
     *
     * @return
     */
    @Bean
    public DefaultRedisScript loadRedisScript() {
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
        // 设置Lua脚本文件路径
        defaultRedisScript.setLocation(new ClassPathResource("ratelimiter.lua"));
        // 设置Lua脚本返回值类型
        defaultRedisScript.setResultType(Boolean.class);
        return defaultRedisScript;
    }
}
