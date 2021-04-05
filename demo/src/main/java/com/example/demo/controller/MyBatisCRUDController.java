package com.example.demo.controller;

import com.example.demo.pojo.Girl;
import com.example.demo.pojo.JSONResult;
import com.example.demo.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/girl")
public class MyBatisCRUDController {
	
	@Autowired
	private GirlService girlService;

	@RequestMapping("/add")
	public JSONResult saveGirl(@RequestBody Girl girl) throws Exception {
		girlService.saveGirl(girl);
		return JSONResult.ok("保存成功");
	}


	@GetMapping("/{id}")
	public Girl  findById(@PathVariable("id") Long id) {
		return girlService.findById(id);
	}

}
