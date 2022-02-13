package com.liul.shiro.autoconfigure;

import com.liul.shiro.cache.RedisCacheManager;
import com.liul.shiro.filter.PermissionOrFilter;
import com.liul.shiro.filter.RoleOrFilter;
import com.liul.shiro.realm.MyCustomRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfiguration {

    private static final int FILTER_ORDER_SHIRO = 3;
    private static final String SHIRO_ROLES_OR_FILTER = "rolesOr";
    private static final String SHIRO_PERMISSION_OR_FILTER = "permsOr";

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisTemplate(redisTemplate);
        return cacheManager;
    }


    // 配置Realm，MD5加密算法
    @Bean("realm1")
    public Realm realm() {
        MyCustomRealm realm = new MyCustomRealm();
        realm.setCredentialsMatcher(md5CredentialsMatcher());

        // 认证禁用缓存
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName("authenticationCache");

        // 授权信息禁用缓存
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName("authorizationCache");
        return realm;
    }

    // 配置Realm,使用SHA1加密算法
//    @Bean("realm2")
//    public Realm realm2() {
//        MyCustomRealm2 realm = new MyCustomRealm2();
//        realm.setCredentialsMatcher(sha1CredentialsMatcher());
//
//        // 认证禁用缓存
//        realm.setAuthenticationCachingEnabled(true);
//        realm.setAuthenticationCacheName("authenticationCache");
//
//        // 授权信息禁用缓存
//        realm.setAuthorizationCachingEnabled(true);
//        realm.setAuthorizationCacheName("authorizationCache");
//
//        return realm;
//    }


    @Bean("md5CredentialsMatcher")
    public CredentialsMatcher md5CredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密算法
        matcher.setHashAlgorithmName("MD5");

        // 设置加密次数
        matcher.setHashIterations(10);
        return matcher;
    }

//    @Bean("sha1CredentialsMatcher")
//    public CredentialsMatcher  sha1CredentialsMatcher (){
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        // 设置加密算法
//        matcher.setHashAlgorithmName("SHA1");
//
//        // 设置加密次数
//        matcher.setHashIterations(10);
//        return matcher;
//    }

    /**
     * 配置securityManager，可以不显示配置
     *
     * @param cacheManager
     * @param realms
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Authenticator authenticator, Collection<Realm> realms, RedisCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置认证器，默认就是ModularRealmAuthenticator，且默认使用AtLeastOneSuccessfulStrategy策略
        // 这里注意setAuthenticator（）与setRealms（）方法的顺序，先执行setAuthenticator（）后执行setRealms()
        // 才能是认证器中的Realms有值
        securityManager.setAuthenticator(authenticator);


        // 设置多个Realm
        securityManager.setRealms(realms);

        //用户认证、授权缓存管理器
        securityManager.setCacheManager(cacheManager);

        return securityManager;
    }


    /**
     * 配置认证器
     *
     * @return
     */
    @Bean
    public Authenticator authenticator() {
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
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
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
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login.html*", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/logout", "logout");   // 使用logout过滤器
        chainDefinition.addPathDefinition("/users/list", "roles[user]"); //需要user角色

//        chainDefinition.addPathDefinition("/users/add", "perms[user:list,user:add]"); //需要user:list和user:add权限
        // 使用自定义权限过滤器， 只需要满足user:list和user:add权限中任意一个即可
        // 增加authc认证主要是防止使用Remember Me 登录后，也可以访问。这里限制只能登录认证才能访问。remember me也不行
        chainDefinition.addPathDefinition("/users/add", "authc, permsOr[user:list,user:add]");


//        chainDefinition.addPathDefinition("/users/deleteBatch", "roles[admin,admin1]"); //需要admin和admin1两种角色
        // 使用自定义角色过滤器，只需要admin或admin1其中之一即可
        chainDefinition.addPathDefinition("/users/deleteBatch", "rolesOr[admin,admin1]");

        // user 过滤器表示已登录或使用Remember me登录都可以访问资源，匹配到规则后，不会执行后面的/**都需要登录认证的规则
        chainDefinition.addPathDefinition("/session", "user");

//        chainDefinition.addPathDefinition("/**", "authc"); //需要登录认证
        return chainDefinition;
    }

    @Bean
    public RoleOrFilter roleOrFilter() {
        return new RoleOrFilter();
    }

    @Bean
    public PermissionOrFilter permissionOrFilter() {
        return new PermissionOrFilter();
    }

    /**
     * 注册shiroFilterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegistrationBean() {
        return createFilterRegistrationBean(new DelegatingFilterProxy("shiroFilter"), FILTER_ORDER_SHIRO);
    }

    private FilterRegistrationBean createFilterRegistrationBean(Filter filter, int order) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        registrationBean.addInitParameter("targetFilterLifecycle", "true");
        registrationBean.setOrder(order);
        registrationBean.setEnabled(true);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(ShiroProperties shiroProperties, DefaultWebSecurityManager securityManager, ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        Map<String, Filter> filters = new HashMap<>();
        filters.put(SHIRO_ROLES_OR_FILTER, roleOrFilter());
        filters.put(SHIRO_PERMISSION_OR_FILTER, permissionOrFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }
}
