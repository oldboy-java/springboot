package com.liuli.boot.secuiry.quickstart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
public class DemoController {

    @GetMapping("/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return "您有p1权限,可以反问/r/r1";
    }

    @PreAuthorize("hasAuthority('p2')")
    @GetMapping("/r2")
    public String r2() {
        return "您有p2权限,可以反问/r/r2";
    }

    @GetMapping("/r3")
    @PreAuthorize("hasAuthority('p2')  and hasAuthority('p2')")
    public String r3() {
        return  "您同时有p1和p2权限，可以访问/r/r3";
    }


    /**
     * 获取登录户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public String getUserInfo() {
        return  getUsername();
    }


    /*** 获取当前登录用户名 */
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
