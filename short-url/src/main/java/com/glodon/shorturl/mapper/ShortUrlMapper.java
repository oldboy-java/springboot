package com.glodon.shorturl.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glodon.shorturl.entity.ShortUrlEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ShortUrlMapper extends BaseMapper<ShortUrlEntity> {


}
