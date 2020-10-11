package com.liul.shiro.configuration;

import com.liul.shiro.realm.MyLoginRealm;
import com.liul.shiro.realm.MyLoginRealm2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class ShiroConfiguration {

    //配置缓存验证器
    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }


    // 配置Realm，MD5加密算法
    @Bean("realm1")
    public Realm realm() {
        MyLoginRealm realm = new MyLoginRealm();
        realm.setCredentialsMatcher(md5CredentialsMatcher());
        return realm;
    }

    // 配置Realm,使用SHA1加密算法
    @Bean("realm2")
    public Realm realm2() {
        MyLoginRealm2 realm = new MyLoginRealm2();
        realm.setCredentialsMatcher(sha1CredentialsMatcher());
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

    // 配置securityManager，可以不用顯示配置
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置认证器，默认就是ModularRealmAuthenticator，且默认使用AtLeastOneSuccessfulStrategy策略
        // 这里注意setAuthenticator（）与setRealms（）方法的顺序，先执行setAuthenticator（）后执行setRealms()
        // 才能是认证器中的Realms有值
        securityManager.setAuthenticator(authenticator());

        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(realm());
        realms.add(realm2());

        // 设置多个Realm
        securityManager.setRealms(realms);

        //设置缓存管理器
        securityManager.setCacheManager(cacheManager());


        return securityManager;
    }


    /**
     * 配置认证器
     * @return
     */
    @Bean
    public Authenticator authenticator (){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());

        // 所有Realm都验证通过，才认证成功
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        return authenticator;
    }

    //启动Shiro的注解(如@RequiresRoles,@RequiresPermissions)
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    // 配置LifecycleBeanPostProcessor，可以自動來調用配置在Spirng IOC 容器中 shiro Bean的生命週期方法。不用顯示配置
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login.html", "anon"); // need to accept POSTs from the login form
        chainDefinition.addPathDefinition("/login", "anon");

        // 使用logout过滤器
        chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }

}
