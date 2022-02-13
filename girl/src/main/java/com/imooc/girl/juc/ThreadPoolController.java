package com.imooc.girl.juc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/juc/thread-pool")
public class ThreadPoolController {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);


    /**
     * 验证时设置jvm参数  -Xmx128m -Xms128m
     *
     * @return
     */
    @RequestMapping("/fix")
    public String fixThreadPool() {
        // 模拟批量下载文件耗时操作
        IntStream.rangeClosed(0, 10000).forEach(r -> {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        return "Success";
    }
}
