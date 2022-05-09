package com.liuli.redis.cluster.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/redis-cluster")
@Api(value = "Redis集群操作", tags = {"Redis集群操作"})
public class RedisClusterController {

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 模拟初始化集群节点数据
     */
    @PostMapping("/init-data")
    public void initRedisClusterData() {
        IntStream.range(0,10).forEach((i)->{
            redisTemplate.opsForValue().set("k" + i,String.valueOf(i));
        });
    }

    /**
     * Jedis集群中使用pipeline会报错
     * Pipeline is currently not supported for JedisClusterConnection.
     *
     * 解决方案使用lettuce
     */
    @PostMapping("/init-data2")
    public void initRedisClusterDataByPipeline() {
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                IntStream.range(0,10).forEach((i)->{
                    redisTemplate.opsForValue().set("k" + i,String.valueOf(i));
                });
                return null;
            }
        });

    }


}
