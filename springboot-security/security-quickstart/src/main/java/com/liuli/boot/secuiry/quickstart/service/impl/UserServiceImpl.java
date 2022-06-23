package com.liuli.boot.secuiry.quickstart.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuli.boot.secuiry.quickstart.entity.UserEntity;
import com.liuli.boot.secuiry.quickstart.repository.UserRepository;
import com.liuli.boot.secuiry.quickstart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserRepository, UserEntity> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUserByUserName(String userName) {
        EntityWrapper<UserEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("username", userName);
        wrapper.last("limit 1");
        return this.selectOne(wrapper);
    }

    @Override
    public List<String> findPermissionsByUserId(Long id) {
        return userRepository.listPermissionsByUserId(id);
    }

}
