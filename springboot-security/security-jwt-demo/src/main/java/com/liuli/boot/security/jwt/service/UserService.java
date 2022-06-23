package com.liuli.boot.security.jwt.service;

import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findUserByUserName(String userName);

    ResponseResult login(UserDTO userDTO);

    List<String> listPermissions(Long id);
}
