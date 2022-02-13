package com.example.demo.controller;

import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ResourceController {

    @Autowired
    private Resource resource;

    @Value("#{resource}")    // 使用resource名称从Spring容器中找Bean
    private Resource url;

    @GetMapping("/getResourceJson")
    public JSONResult getResourceJson() {
        log.info("url={}", url);
        Resource bean = new Resource();
        BeanUtils.copyProperties(resource, bean);
        return JSONResult.ok(bean);
    }
}
