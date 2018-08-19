package com.example.demo.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// 定义每过3秒执行任务
    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        System.out.println(Thread.currentThread().getName()+"-您好" );
    }
    
    
 // 定义每过3秒执行任务
@Scheduled(cron = "4-40 * * * * ?")  
  public void reportCurrentTime2() {
      System.out.println(Thread.currentThread().getName()+"现在时间：" + dateFormat.format(new Date()));
  }
}
