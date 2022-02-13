package com.imooc.girl.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableAsync     //开启异步任务支持
@EnableScheduling  //开启定时任务支持
@Configuration
@Slf4j
public class MyJob {

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     * 每秒执行
     * 1、在Spring定时任务中只支持6位，不支持年
     * 2、在周几的位置上与quartz有差异：1-7 代表周一到周日  ；MON-SUN
     * 3、默认情况下定时任务时阻塞的，主要前一次调度时任务没有执行完，后一次调度阻塞
     * 4、如何不阻塞？
     * 1） 让业务以异步方式提交到自己的线程池
     * CompletableFuture.runAsync(() -> {
     * log.info("hello");
     * // 模拟耗时操作
     * try {
     * TimeUnit.SECONDS.sleep(5);
     * } catch (InterruptedException e) {
     * e.printStackTrace();
     * }
     * }, executor);
     * <p>
     * 2)支持定时任务线程池
     * 自动配置类：TaskSchedulingAutoConfiguration
     * 属性配置：TaskSchedulingProperties
     * spring.task.scheduling.pool.size=5 (不生效)
     * <p>
     * 3）使用异步任务
     *
     * @EnableAsync //开启异步任务支持
     * 自动配置类：TaskExecutionAutoConfiguration
     * 属性配置：TaskExecutionProperties
     * spring.task.execution.pool.core-size=8
     */
//    @Scheduled(cron = "* * * * *  ?")
    @Async  //将方法异步执行，不一定是与定时任务结合使用
    public String hello() throws InterruptedException {
        log.info("hello");

        // 模拟耗时操作
        TimeUnit.SECONDS.sleep(5);

        int i = 9 / 0;

        return "success";


//        CompletableFuture.runAsync(() -> {
//            log.info("hello");
//            // 模拟耗时操作
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, executor);
    }
}
