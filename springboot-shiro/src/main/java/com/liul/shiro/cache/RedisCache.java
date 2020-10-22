package com.liul.shiro.cache;


import com.liul.shiro.utils.ObjectSerializeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;


public class RedisCache<K, V> implements Cache<K, V> {

    private byte[] cacheNameBytes;
    private RedisTemplate<K, V> redisTemplate;
    private Integer db;

    RedisCache(String cacheName, RedisTemplate<K, V> redisTemplate, Integer db) {
        String cacheKey = "SHIRO#" + cacheName;
        this.redisTemplate = redisTemplate;
        cacheNameBytes = redisTemplate.getStringSerializer().serialize(cacheKey);
        this.db = db;
    }

    @Override
    public V get(K k) {
        byte[] fieldBytes = serializeKey(k);

        return redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            byte[] bytes = redisConnection.hGet(cacheNameBytes, fieldBytes);
            if (!ArrayUtils.isEmpty(bytes)) {
                return ObjectSerializeUtils.deserialize(bytes);
            }
            return null;
        }, true);
    }

    @Override
    public V put(K k, V v) {
        byte[] fieldBytes = serializeKey(k);
        redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            redisConnection.hSet(cacheNameBytes, fieldBytes, ObjectSerializeUtils.serialize(v));
            return null;
        }, true);
        return v;
    }

    @Override
    public V remove(K k) {
        byte[] fieldBytes = serializeKey(k);
        return redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            byte[] bytes = redisConnection.hGet(cacheNameBytes, fieldBytes);
            if (!ArrayUtils.isEmpty(bytes)) {
                redisConnection.hDel(cacheNameBytes, fieldBytes);
                return ObjectSerializeUtils.deserialize(bytes);
            }
            return null;
        }, true);
    }

    @Override
    public void clear() {
        redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            redisConnection.del(cacheNameBytes);
            return null;
        }, true);
    }

    @Override
    public int size() {
        Long size = redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            return redisConnection.hLen(cacheNameBytes);
        }, true);
        return (null == size ? 0 : size.intValue());
    }

    @Override
    public Set<K> keys() {
        Set<byte[]> ks = redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            return redisConnection.hKeys(cacheNameBytes);
        }, true);
        if (CollectionUtils.isEmpty(ks)) {
            return Collections.emptySet();
        }
        Set<K> keys = new HashSet<>();
        ks.forEach(k -> keys.add(ObjectSerializeUtils.deserialize(k)));
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<byte[]> values = redisTemplate.execute(redisConnection -> {
            selectDb(redisConnection);
            return redisConnection.hVals(cacheNameBytes);
        }, true);
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        List<V> valueList = new ArrayList<>(values.size());
        values.forEach(v -> valueList.add(ObjectSerializeUtils.deserialize(v)));
        return valueList;
    }

    private byte[] serializeKey(K key) {
        return ObjectSerializeUtils.serialize(key);
    }

    private void selectDb(RedisConnection connection) {
        if (null == db) {
            return;
        }
        connection.select(db);
    }
}
