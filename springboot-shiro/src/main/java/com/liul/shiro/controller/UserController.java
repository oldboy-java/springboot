package com.liul.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/list")
    public String list(){
        return "user_list";
    }

    @GetMapping("/detail")
    public String detail(){
        return "user_detail";
    }


    @GetMapping("/add")
    @RequiresRoles("admin")
    public String add(){
        return "user_add";
    }
}
