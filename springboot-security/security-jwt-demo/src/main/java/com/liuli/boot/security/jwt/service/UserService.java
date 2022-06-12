package com.liuli.boot.security.jwt.service;

import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;

public interface UserService {

     UserDTO findUserByUserName(String userName);

    ResponseResult login(UserDTO userDTO);
}
