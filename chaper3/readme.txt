#########################本工程主要介绍Thymeleaf模板使用##################


使用步骤：
1、添加依赖
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

2、编写模板

    SpringBoot使用上述模板，默认从 src/main/resources/templates下加载


3、开发阶段禁用缓存
spring.thymeleaf.cache=false  ##在每次修改静态内容时按Ctrl+Shift+F9即可重新加载了…

4、查看Thymeleaf默认配置
在application.properties文件中输入spring.thymeleaf弹出的项目，即为默认配置项目

5、配置icon图标
src/main/static/目录下放置一张名为favicon.ico