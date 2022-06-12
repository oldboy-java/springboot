package com.liuli.boot.security.jwt.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuli.boot.cache.RedisCache;
import com.liuli.boot.security.jwt.Mapper.UserMapper;
import com.liuli.boot.security.jwt.dto.LoginUser;
import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl  extends ServiceImpl<UserMapper, UserDTO> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserByUserName(String userName) {
        EntityWrapper<UserDTO> wrapper = new EntityWrapper<>();
        wrapper.eq("username", userName);
        wrapper.last("limit 1");
        return this.selectOne(wrapper);
    }

    @Override
    @SneakyThrows
    public ResponseResult login(UserDTO userDTO) {
        // 使用AuthenticationManager的 authenticate 进行认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                userDTO.getPassword());

        String encode = passwordEncoder.encode(userDTO.getPassword());
        log.info(encode);


        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 认证不成功，提示错误
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }

        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        // 认证成功，使用userId 生成一个jwt
        String token = createJwt(loginUser.getUserDTO().getId());

      // 将用户信息保存到缓存中
       redisCache.set("token:" + token, loginUser);

        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        return ResponseResult.success(map);
    }

    /**
     * 模拟生成Jwt
     * @param id
     * @return
     */
    private String createJwt(String id) {
        return  UUID.randomUUID().toString().replaceAll("-","");
    }
}
