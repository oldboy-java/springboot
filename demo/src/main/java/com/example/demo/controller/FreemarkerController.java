package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.pojo.Resource;

@Controller
@RequestMapping("ftl")
public class FreemarkerController {
	
	@Autowired
	private Resource resource;
	
	
	@GetMapping("index")
	public String index(Model model) {
		model.addAttribute("resource", resource);
		return "freemarker/index";
	}

	@GetMapping("center")
	public String center(Model model) {
		return "freemarker/center/center";
	}
}
