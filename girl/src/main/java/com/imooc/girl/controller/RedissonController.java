package com.imooc.girl.controller;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/lock")
    public String lock() {
        // 1、获取锁，其中key=redssson-lock,value是xxxxxx:当前线程ID
        RLock lock = redissonClient.getLock("redssson-lock");

        //2、加锁
        // lock.lock();  // 阻塞式等待，默认加锁时间是30秒
        // 占锁成功后，会启用一个定时任务重新给锁设置过期时间，新的过期时间就是看门狗的默认超时时间，
        //  每隔10秒（定时器每隔 1/3 * 看门狗超时间 = 10）自动续期到新的30秒不用担心业务时间成，锁自动过期被删除掉
        // 加锁的业务只要运行完成，就不会给当前锁续期，及时不手动解锁，锁会默认在30s后自动删除


        lock.lock(10, TimeUnit.SECONDS); // 阻塞式等待，10秒后，自动解锁，自动解锁时间一定要大于业务执行时间
        // 占用锁后，锁时间到后，不会自动续期

        try {
            System.err.println("加锁成功，执行业务：" + Thread.currentThread().getId());
            TimeUnit.MINUTES.sleep(1);
        } catch (Exception e) {

        } finally {
            // 3、解锁
            lock.unlock();
        }
        return "success";
    }


    /**
     * 模拟停车场景 |  限流
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        //  park.acquire(); //阻塞，获取一个信号（许可），占一个车位
        boolean acquire = park.tryAcquire();  //尝试获取一个信号，获取不到不会阻塞，立即返回
        if (acquire) {
            //执行业务
        } else {
            return "error";
        }
        return "ok=" + acquire;
    }

    /**
     * 模拟停车场景
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/go")
    public String go() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        park.release(); //释放一个车位
        return "ok";
    }


    /**
     * 放假，锁门场景， 指定3个班都走完后，可以锁大门
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/lockdoor")
    public String lockdoor() throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("lock-door");
        door.trySetCount(3);
        door.await();  //一直等待，等待计数为0
        return "放假了。。";
    }

    /**
     * 模拟停车场景
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/gogo/{id}")
    public String gogo(@PathVariable("id") int id) throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("lock-door");
        door.countDown(); //减一
        return id + "班人走完了";
    }


    /**
     * 读写锁，写数据使用写锁
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/write")
    public String write() throws InterruptedException {
        // 获取读写锁
        RReadWriteLock rw = redissonClient.getReadWriteLock("rw-lock");

        // 写数据使用写锁
        RLock writeLock = rw.writeLock();
        String s = null;
        try {
            writeLock.lock();

            TimeUnit.SECONDS.sleep(20);
            s = UUID.randomUUID().toString();
            stringRedisTemplate.opsForValue().set("write-value", s);
        } finally {
            //释放锁
            writeLock.unlock();
        }
        return s;
    }

    /**
     * 读写锁，读数据使用读锁
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/read")
    public String read() throws InterruptedException {
        // 获取读写锁
        RReadWriteLock rw = redissonClient.getReadWriteLock("rw-lock");

        // 读数据使用读锁
        RLock readLock = rw.readLock();
        String s = null;
        try {
            //锁
            readLock.lock();

            TimeUnit.SECONDS.sleep(20);
            s = stringRedisTemplate.opsForValue().get("write-value");
        } finally {
            //释放锁
            readLock.unlock();
        }
        return s;
    }
}
