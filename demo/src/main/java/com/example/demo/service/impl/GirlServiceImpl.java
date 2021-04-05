package com.example.demo.service.impl;

import com.example.demo.mapper.GirlMapper;
import com.example.demo.mapper.GirlMapperCustom;
import com.example.demo.pojo.Girl;
import com.example.demo.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GirlServiceImpl   implements  GirlService {

	@Autowired
	private GirlMapper girlMapper;
	
	@Autowired
	private GirlMapperCustom girlMapperCustom;
	

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class) 
	public void saveGirl(Girl girl) {
		girlMapper.add(girl);
	}

	@Override
	public Girl findById(Long id) {
		return girlMapper.find(id);
	}


}
