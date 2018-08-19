#################本工程主要介绍SpringBoot配置文件读取###################

一、使用命令启动springboot项目

1、切换到项目目录

2、打包
    mvn package

3、启动程序，动态指定启动参数和配置属性

    java -jar springboot-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --server.port=9090
    --my2.age=48


 总结：
  通过命令参数指定的属性 要 优先于配置文件指定的属性




4、本程序默认激活dev开发配置，访问时地址
http://localhost:9090/dev/properties/1





