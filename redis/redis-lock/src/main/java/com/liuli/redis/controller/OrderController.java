package com.liuli.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 模拟下单减库存(无锁)
     *
     * @return
     */
    @GetMapping("/decr-stock")
    public String decrementStock() {
        // 假设redis中初始化了商品p10001的库存
        Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
            log.info("扣减库存成功，剩余库存：" + realStock);
        } else {
            log.info("库存不足，扣减库存失败");
        }
        return "end";
    }

    /**
     * 模拟下单减库存--使用同步代码块
     *
     * @return
     */
    @GetMapping("/decr-stock2")
    public String decrementStock2() {

        synchronized (this) {
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);
            } else {
                log.info("库存不足，扣减库存失败");
            }
        }

        return "end";
    }


    /**
     * 模拟下单减库存--加锁时未设置过期时间
     *
     * @return
     */
    @GetMapping("/decr-stock3")
    public String decrementStock3() {
        String lockKey = "p10001";

        try {
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockKey);
            if (!result) {
                return "error";
            }

            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);

            } else {
                log.info("库存不足，扣减库存失败");
            }
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }


    /**
     * 模拟下单减库存-- 使用Redisson实现分布式锁
     *
     * @return
     */
    @GetMapping("/decr-stock4")
    public String decrementStock4() {
        String lockKey = "p10001";

        RLock lock = null;
        try {
            lock = redissonClient.getLock(lockKey);
            lock.lock();
            ;

            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);

            } else {
                log.info("库存不足，扣减库存失败");
            }
        } finally {
            lock.unlock();
        }
        return "end";
    }


    /**
     * 模拟下单减库存-加锁后，使用expire指令设置过期时间
     *
     * @return
     */
    @GetMapping("/decr-stock5")
    public String decrementStock5() {
        String lockKey = "p10001";
        try {
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockKey);
            stringRedisTemplate.expire(lockKey, 30, TimeUnit.SECONDS);

            if (!result) {
                return "error";
            }
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);
            } else {
                log.info("库存不足，扣减库存失败");
            }
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }


    /**
     * 模拟下单减库存-加锁同时设置过期时间，保证加锁和设置过期时间是原子操作
     *
     * @return
     */
    @GetMapping("/decr-stock6")
    public String decrementStock6() {
        String lockKey = "p10001";
        try {
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockKey, 30, TimeUnit.SECONDS);
            if (!result) {
                return "error";
            }
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);
            } else {
                log.info("库存不足，扣减库存失败");
            }
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }


    /**
     * 模拟下单减库存-加锁时设置唯一标识，删除锁时只有当前锁的锁标识与设置时相同才能删除
     *
     * @return
     */
    @GetMapping("/decr-stock7")
    public String decrementStock7() {
        String lockKey = "p10001";
        String clientId = UUID.randomUUID().toString();
        try {
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS);
            if (!result) {
                return "error";
            }
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减库存成功，剩余库存：" + realStock);
            } else {
                log.info("库存不足，扣减库存失败");
            }
        } finally {
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }
        return "end";
    }


}
