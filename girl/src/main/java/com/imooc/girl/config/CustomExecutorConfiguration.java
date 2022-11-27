package com.imooc.girl.config;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 自定义线程执行器
 */
//@Configuration
public class CustomExecutorConfiguration {

//    @Bean("myTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(TaskExecutorBuilder builder){
        return builder.build();
    }
}
