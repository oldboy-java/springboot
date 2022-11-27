package com.imooc.girl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logger")
@Slf4j
public class LoggerController {

    @GetMapping("/info")
    public String log(){
        log.info(">>>>>>>>>>>>>>>>>>>> 日志级别 info >>>>>>>>>>>>>>>>>>");
        log.debug(">>>>>>>>>>>>>>>>>>>> 日志级别 debug >>>>>>>>>>>>>>>>>>");
        return  "Hello Actuator";
    }
}
