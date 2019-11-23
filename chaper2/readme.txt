#################本工程主要介绍SpringBoot配置文件读取###################

一、使用命令启动springboot项目

1、切换到项目目录

2、打包
    mvn package

3、启动程序，动态指定启动参数和配置属性

    java -jar springboot-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --server.port=9090
    
 总结：
  通过命令参数指定的属性 要 优先于配置文件指定的属性




4、本程序默认激活dev开发配置，访问时地址
http://localhost:9090/dev/properties/

5、springboot默认配置文件名是application.yml或者application.properties,默认配置文件路径
    classpath根目录下、classpath根目录/config   查找application.yml(application.properties)、application-{profile}.properties（(application-{profile}.yml)）

      其他自定义的配置文件，通过@PropertySource指定配置文件路径进行加载。
      
      也可以通过编程的方式来实现配置文件的加载，实现EnvironmentPostProcessor接口加载定义配置文件。需要在src/main/resources/META-INF/spring.factories中
   增加配置：org.springframework.boot.env.EnvironmentPostProcessor=com.liuli.CustomEnvironmentPostProcessor
      

6、配置文件优先级
   6.1  启动jar通过--指定参数 >  application-{profile}  > application   > @PropertySource引入的配置
   
      jar包外的配置 > jar包内的配置 （遵循6.1的优先级）
      
      
7、实验
    7.1 准备
    application-dev.properties 、application-test.properties、application-prod.properties 三个配置文件中
  分别定义m1.age=34-xx ,其中xx为对应的profile值。
  
   application.properties中定义了spring.profiles.active=dev
   
   
   7.2 使用eclipse debug命令启动程序，同时在Debug Configuration的 Program Arguments中定义--spring.profie.active=prod
   
   
   7.3 访问地址
      localhost:9090/properties/1 ,输出：{"age":"34-prod","name":"SkyWalking-prod"}
      
   7.4 结论
       启动jar时指定的配置  优先于 默认application.properties文件中定义的配置，符合6条的结论。
   