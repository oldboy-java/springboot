package com.liuli.boot.security.jwt.config;

import com.liuli.boot.security.jwt.filter.JwtAuthenticationTokenFilter;
import com.liuli.boot.security.jwt.secuity.handler.AuthenticationFailEntryPoint;
import com.liuli.boot.security.jwt.secuity.handler.AuthorizationFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity  //由于使用了Springboot自动装配，此注解可以去掉
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用注解授权
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationFailEntryPoint authenticationFailEntryPoint;

    @Autowired
    private AuthorizationFailHandler authorizationFailHandler;

    /**
     * 配置密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
     * 配置安全拦截机制：指定哪些请求必须认证，哪些请求可以匿名访问
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable() //关闭csrf
                // 不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //登录接口可以匿名访问
                .antMatchers("/user/login").anonymous()
                //其他请求，都必须认证后才能访问
                .anyRequest().authenticated()

                .and()
                // FilterSecurityInterceptor进行授权拦截，从SecurityContextHolder中获取SecurityContext
                .addFilterBefore(jwtAuthenticationTokenFilter, FilterSecurityInterceptor.class)

                // 添加认证失败处理器
                .exceptionHandling().authenticationEntryPoint(authenticationFailEntryPoint)

                // 添加授权异常处理器
            .accessDeniedHandler(authorizationFailHandler);

                // 允许跨域
            http.cors();
    }
}
