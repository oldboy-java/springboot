package com.imooc.girl.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonTest {
    @Autowired
    private RedissonClient redissonClient;


    @Test
    public void testRedissonLock() throws InterruptedException {
        redissonClient.getLock("redisson-lock");

        TimeUnit.MINUTES.sleep(1);
    }
}
