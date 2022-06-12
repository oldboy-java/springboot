package com.liuli.boot.secuiry.quickstart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class HelloSecurityController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello  Spring Security";
    }
}
