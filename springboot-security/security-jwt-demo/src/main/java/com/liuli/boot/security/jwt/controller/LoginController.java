package com.liuli.boot.security.jwt.controller;

import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }
}
