package com.liuli.boot.sharding.keygen;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * 自定义sharding主键生成器（Redis实现）
 */
public class RedisKeyGenerator implements ShardingKeyGenerator {
    private JedisPool jedisPool;
    private static String GENERATOR_KEY = "redis.generator";

    public RedisKeyGenerator(){
        String host = this.getProperties().getProperty("host","localhost");
         String port = this.getProperties().getProperty("port", "6379");
         jedisPool = new JedisPool(new JedisPoolConfig(), host, Integer.parseInt(port));
    }

    private Properties properties = new Properties();

    @Override
    public   synchronized Comparable<?> generateKey() {
        Jedis jedis = null;
        try{
             jedis = jedisPool.getResource();
            return jedis.incr(GENERATOR_KEY);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String getType() {
        return "REDIS";
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
