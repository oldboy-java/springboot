package com.imooc.girl.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证@Async异步任务使用
 */
@RestController
@RequestMapping("/async")
@EnableAsync
public class AsyncController {

    @Autowired
    private ServiceA serviceA;

    @GetMapping("/")
    public String async(){
        return serviceA.a();
    }

}
