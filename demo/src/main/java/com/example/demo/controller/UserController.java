package com.example.demo.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.User;

@Controller
public class UserController {

    @GetMapping("/user")
    @ResponseBody
    public Object getUserInfo() {
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        user.setPassword("123456");
        user.setBirthday(new Date());
        user.setDesc("You are nice");
        return user;
    }

    @GetMapping("/getUserJson")
    @ResponseBody
    public JSONResult getUserJSON() {
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        user.setPassword("123456");
        user.setBirthday(new Date());
        //user.setDesc("You are nice");
        return JSONResult.ok(user);
    }
}
