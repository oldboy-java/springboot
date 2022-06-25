package com.liuli.boot.security.jwt.controller;

import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }


    @PostMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('p4')")
    public ResponseResult hello() {
        return ResponseResult.success("hello");
    }

    @GetMapping("/hello2")
    @PreAuthorize("@spv.hasAuthority('p4')")
    public ResponseResult hello2() {
        return ResponseResult.success("hello2");
    }
}
