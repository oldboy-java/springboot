package com.example.demo.controller;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Girl;
import com.example.demo.pojo.JSONResult;
import com.example.demo.service.GirlService;

@RestController
@RequestMapping("mybatis")
public class MyBatisCRUDController {
	
	@Autowired
	private GirlService girlService;
	
	@Autowired
	private Sid sid;
	
	@RequestMapping("/saveGirl")
	public JSONResult saveGirl() throws Exception {
		String girlId = sid.nextShort();
		Girl girl = new Girl();
		girl.setId(girlId);
		girl.setAge(26);
		girl.setCupSize("AA");
		girlService.saveGirl(girl);
		return JSONResult.ok("保存成功");
	}
	
	@RequestMapping("/updateGirl")
	public JSONResult updateGirl() {
		Girl girl = new Girl();
		girl.setId("180506AAKNMS86FW");
		girl.setAge(36);
		girlService.updateGirl(girl);
		return JSONResult.ok("保存成功");
	}
	
	@RequestMapping("/deleteGirl")
	public JSONResult deleteGirl(String girlId) {
		girlService.deleteGirl(girlId);
		return JSONResult.ok("删除成功");
	}
	
	@RequestMapping("/queryGirlById")
	public JSONResult queryGirlById(String girlId) {
		return JSONResult.ok(girlService.queryGirlById(girlId));
	}
	
	@RequestMapping("/queryGirlList")
	public JSONResult queryGirlList() {
		Girl girl = new Girl();
		girl.setCupSize("C");
		List<Girl> girlList = girlService.queryGirlList(girl);
		return JSONResult.ok(girlList);
	}
	
	@RequestMapping("/queryGirlListPaged")
	public JSONResult queryGirlListPaged(@RequestParam(name="page",defaultValue="1")Integer page,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize) {
		Girl girl = new Girl();
		List<Girl> girlList = girlService.queryGirlListPaged(girl, page, pageSize);
		return JSONResult.ok(girlList);
	}
	
	@RequestMapping("/queryGirlListByCupSize")
	public JSONResult queryGirlListByIdCustom(String cupSize,@RequestParam(name="page",defaultValue="1")Integer page,
			@RequestParam(name="pageSize",defaultValue="2")Integer pageSize) {
		return JSONResult.ok(girlService.queryGirlListByCupSizePaged(cupSize, page, pageSize));
	}
	
	
	
	@RequestMapping("/saveGirlTransactional")
	public JSONResult saveGirlTransactional() throws Exception{
		String girlId = sid.nextShort();
		Girl girl = new Girl();
		girl.setId(girlId);
		girl.setAge(34);
		girl.setCupSize("CCC");
		girlService.saveGirlTransactional(girl);
		return JSONResult.ok("保存成功");
	}
}
