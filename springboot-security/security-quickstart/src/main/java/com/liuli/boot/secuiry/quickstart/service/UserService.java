package com.liuli.boot.secuiry.quickstart.service;

import com.liuli.boot.secuiry.quickstart.entity.UserEntity;

import java.util.List;

public interface UserService {

     UserEntity findUserByUserName(String userName);

    List<String> findPermissionsByUserId(Long id);
}
