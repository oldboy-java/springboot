package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("th")
public class ThymeleafController {


    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("name", "thymeleaf");
        return "thymeleaf/index";
    }

    @GetMapping("center")
    public String center(Model model) {
        return "thymeleaf/center/center";
    }
}
