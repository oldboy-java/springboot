#################本工程主要介绍SpringBoot Actuator 特性使用##################

一、Actuator Endpoint
  1、作用
    监控管理应用程序
    
  2、访问方式
   HTTP 、 JMX
   
  3、常用Endpoint
   ID              说明                                                                    默认开启          默认 HTTP     默认JMX
   beans           显示容器中的Bean列表                                      Y             N            Y
 
   caches          显示应用中的缓存（Cacheable注解）              Y             N            Y
   
   
   configprops     显示@ConfigurationProperties的信息          Y             N            Y
   
   env             显示ConfigurableEnvironment中的信息        Y             N            Y
   
   
   health         显示健康检查信息                                                  Y            Y           Y
   
   
   info           显示设置好的应用信息                                          Y             Y           Y
   
   
   scheduledtasks   显示应用的调度任务信息                                 Y             N            Y
   
   
   threaddump       执行Thread Dump                       Y             N            Y
   


   4、如何访问
   HTTP访问
   /actuator/<id>
   
  5、配置端口与路径 （将Actuator与应用端口分离，可以进行配置）
  management.server.address=
  management.server.port=
  management.endpoints.web.base-path=/actuator
  management.endpoints.web.path-mapping.<id>=路径
  
  6、开启Endpoint
  management.endpoint.<id>.enabled=true (开启)
  management.endpoints.enabled-by-default=false
  
  7、暴露Endpoint
  JMX暴露：
  management.endpoints.jmx.exposure.exclude=     （取消暴露，这里填写id，多个用逗号隔开）
  management.endpoints.jmx.exposure.include=*  （暴露所有的）
  
  HTTP暴露
  management.endpoints.web.exposure.exclude=     （取消暴露，这里填写id，多个用逗号隔开）
  management.endpoints.web.exposure.include=info, health
  
  
  8、SpringBoot自带的Health Indicator
    
    8.1 目的
               检查应用程序的运行状态
    
   8.2状态
      DOWN             -503
      OUT_OF_SERVICE   -503
      
      UP               -200
      UNKNOWN          -200
      
   8.3 机制
      通过HealthIndicatorRegistry收集信息
      
   HealthIndicator实现具体检查逻辑
   
   8.4 配置项
      management.health.defaults.enable=true | false
      
      management.health.<id>.enable=true  #这里的ID指向内置的HealthIndicator
      
      management.endpoint.health.show-details=never| when-authorized | always
      
   8.5 内置HealthIndicator
    DataSourceHealthIndicator 、 DiskSpaceHealthIndicator 、RedisHealthIndicator
    
    MongoHealthIndicator、 ElasticsearchHealthIndicator
    
    
   8.6 定制自己的HealthIndicator
      实现HealthIndicator接口，覆盖health方法
   
      
      
  
  
    
   

   
   
   