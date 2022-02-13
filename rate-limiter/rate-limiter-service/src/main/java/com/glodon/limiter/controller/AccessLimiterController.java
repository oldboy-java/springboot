package com.glodon.limiter.controller;

import com.glodon.limiter.annatation.AccessLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class AccessLimiterController {

    /**
     * 每秒内指定次数进行限流
     *
     * @return
     */
    @GetMapping("/access-limit/seconds")
    @AccessLimiter(limit = 2, timeUnit = TimeUnit.SECONDS, methodKey = "accessLimit")
    public String accessLimitInSeconds() {
        return "success";
    }


    /**
     * 每分钟内指定次数进行限流
     *
     * @return
     */
    @GetMapping("/access-limit/minutes")
    @AccessLimiter(limit = 1, timeUnit = TimeUnit.MINUTES)
    public String accessLimitInMinutes(String xx, String bb) {
        return "success";
    }
}
