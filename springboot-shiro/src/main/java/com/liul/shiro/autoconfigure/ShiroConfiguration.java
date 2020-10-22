package com.liul.shiro.autoconfigure;

import com.liul.shiro.cache.RedisCacheManager;
import com.liul.shiro.realm.MyLoginRealm;
import com.liul.shiro.realm.MyLoginRealm2;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class ShiroConfiguration {

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisTemplate(redisTemplate);
        return cacheManager;
    }


    // 配置Realm，MD5加密算法
    @Bean("realm1")
    public Realm realm() {
        MyLoginRealm realm = new MyLoginRealm();
        realm.setCredentialsMatcher(md5CredentialsMatcher());

        // 认证禁用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthenticationCacheName("authenticationCache");

        // 授权信息禁用缓存
        realm.setAuthorizationCachingEnabled(false);
        realm.setAuthorizationCacheName("authorizationCache");
        return realm;
    }

    // 配置Realm,使用SHA1加密算法
    @Bean("realm2")
    public Realm realm2() {
        MyLoginRealm2 realm = new MyLoginRealm2();
        realm.setCredentialsMatcher(sha1CredentialsMatcher());

        // 认证禁用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthenticationCacheName("authenticationCache");

        // 授权信息禁用缓存
        realm.setAuthorizationCachingEnabled(false);
        realm.setAuthorizationCacheName("authorizationCache");

        return realm;
    }


    @Bean("md5CredentialsMatcher")
    public CredentialsMatcher  md5CredentialsMatcher (){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密算法
        matcher.setHashAlgorithmName("MD5");

        // 设置加密次数
        matcher.setHashIterations(10);
        return matcher;
    }

    @Bean("sha1CredentialsMatcher")
    public CredentialsMatcher  sha1CredentialsMatcher (){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密算法
        matcher.setHashAlgorithmName("SHA1");

        // 设置加密次数
        matcher.setHashIterations(10);
        return matcher;
    }

    /**
     *  配置securityManager，可以不显示配置
     * @param cacheManager
     * @param realms
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(RedisCacheManager cacheManager,  Collection<Realm> realms ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置认证器，默认就是ModularRealmAuthenticator，且默认使用AtLeastOneSuccessfulStrategy策略
        // 这里注意setAuthenticator（）与setRealms（）方法的顺序，先执行setAuthenticator（）后执行setRealms()
        // 才能是认证器中的Realms有值
        securityManager.setAuthenticator(authenticator());


        // 设置多个Realm
        securityManager.setRealms(realms);

        //用户认证、授权缓存管理器
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }


    /**
     * 配置认证器
     * @return
     */
    @Bean
    public Authenticator authenticator (){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());

        // 所有Realm都验证通过，才认证成功
//        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        return authenticator;
    }


    /**
     * 启用权限注解控制 @RequiresRoles,@RequiresPermissions
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 启用权限注解控制
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    /**
     * 配置LifecycleBeanPostProcessor，可以自動來調用配置在Spirng IOC 容器中 shiro Bean的生命週期方法。不用顯示配置
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login.html", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/logout", "logout");   // 使用logout过滤器
        chainDefinition.addPathDefinition("/users/**", "anon");
        chainDefinition.addPathDefinition("/**", "authc"); //需要登录认证
        return chainDefinition;
    }


}
