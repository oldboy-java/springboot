1、使用前后端模式

2、不使用Session进行会话管理
`@Override
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
.anyRequest().authenticated();
}`


3、使用JWT存储用户信息