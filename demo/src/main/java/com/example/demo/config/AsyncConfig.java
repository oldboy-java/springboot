package com.example.demo.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

	 private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);
	 
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor(); 
		threadPool.setCorePoolSize(2);//当前线程数
        threadPool.setMaxPoolSize(120);// 最大线程数
        threadPool.setQueueCapacity(1);//线程池所使用的缓冲队列
        threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setThreadNamePrefix("MyAsync-");//  线程名称前缀
        threadPool.initialize(); // 初始化
        return threadPool;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				System.out.println("-------------》》》捕获线程异常信息");
	            log.info("Exception message - " + ex.getMessage());
	            log.info("Method name - " + method.getName());
	            for (Object param : params) {
	                log.info("Parameter value - " + param);
	            }
			}
		};
	}

}
