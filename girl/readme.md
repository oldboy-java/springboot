一、启动springboot应用方法

1、直接运行GirlApplication.java这个类中的main方法

2、进入到D:\Program Files\workspace\girl目录下，使用命令： mvn spring-boot:run

3、使用jar命令运行 第一步：进入到D:\Program Files\workspace\girl,使用mvn install安装 第二步：进入D:\Program Files\workspace\girl\target下，使用命令
java -jar girl-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

其中--spring.profiles.active=prod指定使用哪种启动配置文件

二、 AOP处理 1、 pom.xml中配置：

 		<!-- aop处理 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>

2、定义类，在类上增加@Aspect和@Component注解

3、定义切入点@Pointcut

	@Pointcut("execution(public * com.imooc.girl.controller..*.*(..))") 
	public void log() {
		
	}
	
	execution(public * com.imooc.girl.controller..*.*(..))
	定义com.imooc.girl.controller包及子包下面的所有类所有方法

4、定义Before方法 @Before("log()")
public void doBefore(JoinPoint joinPoint) { //定义执行调用请求方法之前进行的拦截操作  
}

5、定义After方法 @After("log()")
public void doAfter(JoinPoint joinPoint) { //定义执行调用请求方法后，返回給客户端（浏览器等）时进行的拦截操作 }

6、定义AfterReturing方法获取请求方法的返回值 //这里的Object是定义请求方法的返回值类型，其中returning的值等于参数obj @AfterReturning(pointcut="log()",returning="
obj")
public void doAfterReturning(Object obj) { //对义请求方法返回值obj进行相关处理 }

三、异常 Spring只针对RuntimeException进行回滚，因此自定义异常时必须是RuntimeException

四、增加自动配置 META-INF/spring.factories中配置 org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.imooc.girl.config.Bean1AutoConfiguration,\ #定义自动配置类1 com.imooc.girl.config.Bean2AutoConfiguration
#定义自动配置类2，需要注意最后一个配置不需要后面的

五、集成Sentinel进行流量控制

       <dependency>
                      	<groupId>com.alibaba.cloud</groupId>
                        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
                         <version>2.2.4.RELEASE</version>
      </dependency>                                         	
	

