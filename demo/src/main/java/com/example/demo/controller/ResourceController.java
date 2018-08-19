package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.Resource;

@RestController
public class ResourceController {

	@Autowired
	private Resource resource;
	
	@GetMapping("/getResourceJson")
	public JSONResult getResourceJson() {
		Resource bean = new Resource();
		BeanUtils.copyProperties(resource, bean);
		return JSONResult.ok(bean);
	}
}
