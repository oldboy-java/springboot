package com.liuli.controller;

import com.liuli.pojo.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018-8-21.
 */
@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

    @GetMapping(value = "index")
    public String index(Model model){
        Author author = new Author();
        author.setName("SKY名声");
        author.setAge(29);
        author.setEmail("888888@qq.com");

        String title ="Thymeleaf模板使用";
        String desc = "使用Thymeleaf模板开发WEB页面";

        model.addAttribute("author",author);
        model.addAttribute("title",title);
        model.addAttribute("desc",desc);
        return "index";
    }

}
