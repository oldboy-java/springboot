package com.liuli.boot.secuiry.quickstart.secuity;

import com.liuli.boot.secuiry.quickstart.dto.UserDTO;
import com.liuli.boot.secuiry.quickstart.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetail  implements UserDetails {

    private UserDTO user;

    public CustomUserDetail(UserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = user.getAuthorities();
        if (!CollectionUtils.isEmpty(authorities)) {
          return   AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()]));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
