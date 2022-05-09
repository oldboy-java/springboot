package com.liuli.boot.sharding.mapper;

import com.liuli.boot.sharding.entity.Dict;
import com.liuli.boot.sharding.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DictMapper {

    @Insert("insert into t_dict (f_code,f_value) values(#{dict.code},#{dict.value})")
     Integer insertDict(@Param("dict") Dict dict);

    @Delete("delete from t_dict where f_id=#{dictId}")
    void deleteDict(@Param("dictId") Long dictId);
}
