package com.liul.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义多种角色满足或关系即授权成功，默认shiro使用多种角色与的关系校验
 */
public class RoleOrFilter  extends AuthorizationFilter {
    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param o  是当前资源访问需要角色
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        // 获取Subject
        Subject subject =  this.getSubject(servletRequest, servletResponse);

        String[] roles = (String[])o;
        // 角色为空，说明不需要角色认证，直接可以访问
        if (roles .length == 0) {
            return true;
        }
        for (String role : roles) {
            // 只要用户有其中一个角色，即可以访问资源
            if (subject.hasRole(role) ) {
                return true;
            }
        }
        return false;
    }
}
