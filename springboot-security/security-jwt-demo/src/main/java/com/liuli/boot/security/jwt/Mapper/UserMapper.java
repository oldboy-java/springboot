package com.liuli.boot.security.jwt.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liuli.boot.security.jwt.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO> {
}
