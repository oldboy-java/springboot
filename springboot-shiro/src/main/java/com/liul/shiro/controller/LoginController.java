/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.liul.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;


@Controller
@Slf4j
public class LoginController {

    @RequestMapping("/login.html")
    public String loginTemplate() {

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String pwd, Model model){
        log.info("username={}, password={}", username, pwd);
        String error = null;
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
            token.setRememberMe(true);
            try {
                user.login(token);
            }catch (AuthenticationException ae) {
                if (ae instanceof IncorrectCredentialsException) {
                    log.error("login failed={}", "用户凭证不正确");
                    error = "用户凭证不正确";
                }  else if (ae instanceof UnknownAccountException) {
                    log.error("login failed={}", "用户不存在");
                    error ="用户不存在";
                } else if (ae instanceof LockedAccountException) {
                    log.error("login failed={}", "用户账号被锁定");
                    error ="用户账号被锁定";
                }
                // 设置错误信息，前台页面给与错误提示
                model.addAttribute("error", error);
                return "login";
            }

        }
        return "redirect:/";
    }

}
