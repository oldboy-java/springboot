package com.liul.shiro.realm;

import com.liul.shiro.utils.SerializableByteSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 这里仅仅只做认证的话，直接继承 AuthenticatingRealm
 * 仅仅是模拟角色、权限
 */
@Slf4j
public class MyCustomRealm2 extends AuthorizingRealm {

    // authenticationToken 这里的token就是执行login中传递过来的token
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1. 把AuthenticationToken 转成UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        // 2. 从UsernamePasswordToken中获取用户名
        String username = upToken.getUsername();

        // 3. 根据username从数据库中查找用户记录
        // 模拟从数据库中获取用户
        System.out.println("从数据库中获取 username:" + username + "对应的用户信息");


        // 4. 若用户不存在，则抛出UnknownAccountException 异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }

        // 5. 根据用户信息情况，决定是否抛出AuthenticationException 异常
        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }


        // 6. 根据用户情况，来构建 AuthenticationInfo 对象并返回,通常使用实现类：SimpleAuthenticationInfo
        // 1) principal:认证的实体信息，可以是username，也可以是数据表对应的用户的实体类对象
        Object principal = username;

        // 2) credentials ：密码，这个密码是从数据库中查询出来的用户密码
//        ByteSource salt = ByteSource.Util.bytes(username);
        SerializableByteSource salt = new SerializableByteSource(username);
        Object credentials = getCiphertext("SHA1", "123456", salt, 10);

        // 3) realName: 当前realm对象的name.调用父类的getName()即可
        String realmName = getName();

        // 返回AuthenticationInfo对象后，由shiro完成密码的比对工作
        // 具体由AuthenticatingRealm的credentialsMatcher属性来进行比对
        // 使用盐值加密 :设置salt

        // 这里模拟认证失败的情况：设置credentials=888888888， principal=Realm2
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, salt, realmName);
        return info;
    }


    private Object getCiphertext(String algorithmName, Object source, Object salt, int hashIterations) {
        Object result = new SimpleHash(algorithmName, source, salt, hashIterations);
        return result;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
