package com.example.demo.mapper;


import com.example.demo.pojo.Girl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GirlMapper {

    public void add(Girl girl);

    Girl find(@Param("id") Long id);

    void addByBatch(List<Girl> girls);

}