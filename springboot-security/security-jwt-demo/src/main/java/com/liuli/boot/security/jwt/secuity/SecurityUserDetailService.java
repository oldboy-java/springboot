package com.liuli.boot.security.jwt.secuity;

import com.liuli.boot.security.jwt.dto.LoginUser;
import com.liuli.boot.security.jwt.dto.UserDTO;
import com.liuli.boot.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findUserByUserName(username);
        if (user == null) {
            return null;
        }
      return new LoginUser(user);
    }
}
