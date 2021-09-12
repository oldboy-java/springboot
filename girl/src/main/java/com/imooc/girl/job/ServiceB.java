package com.imooc.girl.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ServiceB {

    @Async
    public  CompletableFuture<String>  b(int c){
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "b";
        });

        int i = 1 /0;

        return future;
    }
}
