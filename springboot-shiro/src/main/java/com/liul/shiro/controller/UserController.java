package com.liul.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/list")
    public String list(){
        return "user_list";
    }

    @GetMapping("/detail")
    @RequiresPermissions("user:detail")
    public String detail(){
        return "user_detail";
    }


    @GetMapping("/add")
    public String add(){
        return "user_add";
    }

    @GetMapping("/delete")
    @RequiresRoles("admin")
    @ResponseBody
    public String delete(){
        return "delete success";
    }


    /**
     * 测试批量添加默认RequiresRoles需要两种角色才有权限.通过注解限制角色
     * @return
     */
    @GetMapping("/addBatch")
    @RequiresRoles({"admin","admin1"})
    @ResponseBody
    public String addBatch(){
        return "addBatch success";
    }


    /**
     * 测试批量添加默认RequiresRoles需要两种角色才有权限。通过在shiro配置中针对url进行角色配置。在
     * 配置中可以使用自定义的过滤器，实现多个角色只要满足其中一个即可。见ShiroConfiguration配置
     * @return
     */
    @GetMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(){
        return "deleteBatch success";
    }
}
