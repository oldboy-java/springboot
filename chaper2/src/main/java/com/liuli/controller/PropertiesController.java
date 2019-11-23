package com.liuli.controller;

import com.liuli.properties.CustomProperties;
import com.liuli.properties.MyProperties1;
import com.liuli.properties.MyProperties2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-8-8.
 */
@RestController
@RequestMapping("/properties")
public class PropertiesController {
    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);
    
    @Autowired
    private MyProperties1 myProperties1;
    
    @Autowired
    private MyProperties2 myProperties2;
    
    @Autowired
    private CustomProperties customProperties;

   

    @GetMapping("/1")
    public MyProperties1 getMyProperties1(){
        log.info("======================================================");
        log.info(myProperties1.toString());
        log.info("======================================================");
        return myProperties1;
    }
    @GetMapping("/2")
    public MyProperties2 getMyProperties2(){
        log.debug("======================================================");
        log.debug(myProperties2.toString());
        log.debug("======================================================");
        return myProperties2;
    }
    
    
    @GetMapping("/custom")
    public CustomProperties getCustomProperties(){
        log.info("======================================================");
        log.info(customProperties.toString());
        log.info("======================================================");
       
        //customProperties 返回的是代理对象，直接返回JSON报错，转换下
        CustomProperties custom = new CustomProperties();
        custom.setAge(customProperties.getAge());
        custom.setName(customProperties.getName());
        
        return custom;
    }
}
