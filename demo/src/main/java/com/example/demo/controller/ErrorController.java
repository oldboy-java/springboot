package com.example.demo.controller;

import com.example.demo.pojo.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("err")
public class ErrorController {

    @GetMapping("error")
    public String error(Model model) {
        int i = 10 / 0;
        return "thymeleaf/error";
    }

    @RequestMapping("/ajaxerror")
    public String ajaxerror(Model model) {
        return "thymeleaf/ajaxerror";
    }

    @RequestMapping("/getAjaxerror")
    @ResponseBody
    public JSONResult getAjaxerror(Model model) {
        int i = 10 / 0;
        return JSONResult.ok();

    }
}
