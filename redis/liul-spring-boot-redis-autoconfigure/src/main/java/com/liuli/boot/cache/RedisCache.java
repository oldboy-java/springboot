package com.liuli.boot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisCache<T> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public void set(String key, T value) {
        ValueOperations<String, T> stringTValueOperations = redisTemplate.opsForValue();
        stringTValueOperations.set(key, value);
    }

    public Object get(String key) {
        ValueOperations<String, T> stringTValueOperations = redisTemplate.opsForValue();
        return stringTValueOperations.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
