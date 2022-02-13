package com.liuli.tk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/html/ifeng")
    @ResponseBody
    public String getBaiduIndexHtml() {

        // 这里只能获取直连的RestTemplate，否则会报错，因为这里是直接访问某个URL。对于Loadbalanced类型的只能通过服务发现的方式进行调用
        return restTemplate.getForObject("https://www.ifeng.com/", String.class);
    }
}