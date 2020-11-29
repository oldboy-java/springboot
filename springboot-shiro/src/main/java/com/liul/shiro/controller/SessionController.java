package com.liul.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping

    //使用此注解时，shiroConfiguration中配置的/**也会起作用，起不到Remember me可以访问的目的
    // 排除其他影响，将配置中的/** 需要登录的配置需要注释掉
   @RequiresUser
    public String  session(HttpSession session) {
        // 使用HttpSession设置属性值
        session.setAttribute("session", session.getId());

        // 模拟在Service层中通过Shiro获取HttpSession中的属性值
        Subject subject = SecurityUtils.getSubject();
        Session  shiroSession = subject.getSession();

        return  (String)shiroSession.getAttribute("session");
    }
}
