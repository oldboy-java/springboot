package com.liul.shiro.realm;

import com.liul.shiro.mapper.UserMapper;
import com.liul.shiro.po.User;
import com.liul.shiro.utils.SerializableByteSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 这里仅仅只做认证的话，直接继承 AuthenticatingRealm，如果也需要做授权则继承AuthorizingRealm
 * 从数据库中获取用户角色、权限相关信息
 * AuthorizingRealm extends AuthenticatingRealm
 */
@Slf4j
public class MyCustomRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    // authenticationToken 这里的token就是执行login中传递过来的token
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 把AuthenticationToken 转成UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        log.info(upToken.getCredentials().toString());
        // 2. 从UsernamePasswordToken中获取用户名
        String username = upToken.getUsername();

        // 3. 根据username从数据库中查找用户记录
        User user = getUser(username);

        // 4. 若用户不存在，则抛出UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

        // 由shiro完成密码不对工作

        // 5. 根据用户信息情况，决定是否抛出AuthenticationException 异常 ,这里假设status=1时账号被锁定
        if (user.getStatus() == 1) {
            throw new LockedAccountException("用户被锁定");
        }


        // 6. 根据用户情况，来构建 AuthenticationInfo 对象并返回,通常使用实现类：SimpleAuthenticationInfo
        // 1) principal:认证的实体信息，可以是username，也可以是数据表对应的用户的实体类对象
        Object principal = username;

        // 2) credentials ：密码，这个密码是从数据库中查询出来的用户密码
        Object credentials = user.getPassword();

        // 3) realName: 当前realm对象的name.调用父类的getName()即可
        String realmName = getName();


        // 使用盐值加密 :设置salt
        //        ByteSource salt = ByteSource.Util.bytes(username);
        // 使用缓存用ByteSource会java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
        SerializableByteSource credentialsSalt = new SerializableByteSource(username);

        // 返回AuthenticationInfo对象后，由shiro完成密码的比对工作
        // 具体由AuthenticatingRealm的credentialsMatcher属性来进行比对
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

        return info;
    }


    /**
     * 模拟从数据库中获取用户
     */
    private User getUser(String username) {
        User user = userMapper.findUserByUserName(username);
        log.info("从数据库中获取 username[" + username + "]对应的用户信息={}", user);
        return user;
    }


    /**
     * 进行授权处理
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1. 从PrincipalCollection 获取登录用户信息
        Object principal = principalCollection.getPrimaryPrincipal();

        // 2. 根据登录用户信息来获取当前用户的角色和权限
        Set<String> roles = getRolesByUsername((String) principal);
        Set<String> permissions = getPermissionsByUsername((String) principal);


        // 3. 创建SimpleAuthorizationInfo，并设置roles和permissions
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);

        // 4. 返回SimpleAuthorizationInfo对象
        return info;
    }


    /**
     * 模拟从数据库(缓存中)中获取角色
     *
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username) {
        Set<String> roles = new HashSet<String>();
        List<String> rList = userMapper.getUserRoles(username);
        if (!CollectionUtils.isEmpty(rList)) {
            roles.addAll(rList);
        }
        return roles;
    }


    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissions = userMapper.getPermissions(username);
        return permissions;
    }
}
