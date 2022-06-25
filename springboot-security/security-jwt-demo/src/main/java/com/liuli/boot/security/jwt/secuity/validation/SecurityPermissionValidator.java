package com.liuli.boot.security.jwt.secuity.validation;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 自定义权限Validator，可参考SecurityExpressionRoot实现对应方法
 */
@Component("spv")
public class SecurityPermissionValidator {

    /**
     * 结合实际情况来进行权限处理逻辑
     * @param authority  权限项
     * @return
     */
    public final boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        if (CollectionUtils.isEmpty(grantedAuthorities)) {
            return Boolean.FALSE;
        }
        Set<String> permissions = AuthorityUtils.authorityListToSet(grantedAuthorities);
        return permissions.contains(authority);
    }

}
