package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.Girl;

public interface GirlMapperCustom  {
	
	public List<Girl> selectByCupSize(@Param("cupSize")String cupSize);
}