package com.liul.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisCacheManager extends AbstractCacheManager {
    private RedisTemplate redisTemplate;
    private Integer db;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setDb(Integer db) {
        this.db = db;
    }

    @Override
    protected Cache createCache(String cacheName) {
        return new RedisCache(cacheName, redisTemplate, db);
    }
}
