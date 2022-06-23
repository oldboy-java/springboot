package com.liuli.boot.security.jwt.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuli.boot.cache.RedisCache;
import com.liuli.boot.security.jwt.Mapper.UserMapper;
import com.liuli.boot.security.jwt.secuity.userdetails.LoginUserDetail;
import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.entity.UserEntity;
import com.liuli.boot.security.jwt.service.UserService;
import com.liuli.boot.security.jwt.utils.JwtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl  extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RedisCache redisCache;

  @Autowired
  private UserMapper userMapper;

    @Override
    public UserEntity findUserByUserName(String userName) {
        EntityWrapper<UserEntity> wrapper = new EntityWrapper<>();
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

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 认证不成功，提示错误
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }

        // 获取认证信息，其值就是UserDetailService的返回值
        LoginUserDetail loginUser = (LoginUserDetail)authenticate.getPrincipal();

        // 认证成功，使用userId 生成一个jwt
        String token = createJwt(loginUser.getUserDTO().getId());

      // 将用户信息保存到缓存中
       redisCache.set("login:" + loginUser.getUserDTO().getId(), loginUser);

        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        return ResponseResult.success(map);
    }

    @Override
    public List<String> listPermissions(Long id) {
        return userMapper.listPermissionsByUserId(id);
    }

    /**
     * 模拟生成Jwt
     * @param id
     * @return
     */
    private String createJwt(Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", id);
        return JwtUtils.createJwtToken(data, null, 10000 * 120L);
    }
}
