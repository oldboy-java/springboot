package com.imooc.girl.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ServiceA {

    @Autowired
    private ServiceB serviceB;


    public String a() {
        CompletableFuture<String> future = serviceB.b(30);

        System.err.println(future);
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
