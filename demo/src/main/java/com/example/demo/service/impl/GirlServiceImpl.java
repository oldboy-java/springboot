package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.example.demo.mapper.GirlMapper;
import com.example.demo.mapper.GirlMapperCustom;
import com.example.demo.pojo.Girl;
import com.example.demo.service.GirlService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;


@Service
public class GirlServiceImpl implements GirlService {

	@Autowired
	private GirlMapper girlMapper;
	
	@Autowired
	private GirlMapperCustom girlMapperCustom;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class) 
	public void saveGirl(Girl girl) {
		girlMapper.insert(girl);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class) 
	public void updateGirl(Girl girl) {
		girlMapper.updateByPrimaryKeySelective(girl);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class) 
	public void deleteGirl(String girlId) {
		girlMapper.deleteByPrimaryKey(girlId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Girl queryGirlById(String girlId) {
		return girlMapper.selectByPrimaryKey(girlId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Girl> queryGirlList(Girl girl) {
		Example example = new Example(Girl.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmptyOrWhitespace(girl.getCupSize())) {
			criteria.andLike("cupSize", "%".concat(girl.getCupSize()).concat("%"));
		}
		return girlMapper.selectByExample(example);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Girl> queryGirlListPaged(Girl girl, Integer page, Integer pageSize) {
		//设置分页
		PageHelper.startPage(page, pageSize);
		
		Example example = new Example(Girl.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmptyOrWhitespace(girl.getCupSize())) {
			criteria.andLike("cupSize", "%".concat(girl.getCupSize()).concat("%"));
		}
		//设置按年龄降序
		example.orderBy("age").desc();
		return girlMapper.selectByExample(example);
	}

	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Girl> queryGirlListByCupSizePaged(String cupSize, Integer page, Integer pageSize) {
		//设置分页
		PageHelper.startPage(page, pageSize);
		return girlMapperCustom.selectByCupSize(cupSize);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class) 
	//默认只针对运行时异常进行回滚，通过rollbackFor设置回滚发生的异常类型,这里定义所有Exception类型异常都进行回滚
	public void saveGirlTransactional(Girl girl) throws Exception{
		girlMapper.insert(girl);
		throw new RuntimeException("抛出异常");
	}
}
