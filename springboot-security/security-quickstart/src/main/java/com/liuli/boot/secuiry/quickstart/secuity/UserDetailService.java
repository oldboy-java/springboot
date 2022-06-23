package com.liuli.boot.secuiry.quickstart.secuity;

import com.liuli.boot.secuiry.quickstart.dto.UserDTO;
import com.liuli.boot.secuiry.quickstart.entity.UserEntity;
import com.liuli.boot.secuiry.quickstart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findUserByUserName(username);
        if (user == null) {
            return null;
        }
        List<String> authorities = userService.findPermissionsByUserId(user.getId());
        UserDTO userDTO = UserDTO.builder().username(user.getUsername()).password(user.getPassword())
                .id(user.getId()).mobile(user.getMobile()).fullname(user.getFullname())
                .authorities(authorities).build();
        return new CustomUserDetail(userDTO);
    }
}
