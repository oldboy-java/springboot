1、集成定时任务

	     第一步：启动类增加@EnableScheduling注解
	     第二步：编写ScheduleConfig进行多线程执行任务配置，类上增加@Configuration
	     第三步：编写定时任务类（增加@Component注解、定时任务方法上增加@Scheduled，进行定时任务调度配置）
	     
	     附： 在线cron表达式： http://cron.qqe2.com/

2、集成异步任务

	   第一步：启动类增加@EnableAsync注解
	   第二步：编写AsyncConfig进行多线程执行任务配置，类上增加@Configuration
	   第三步：编写任务类（增加@Component注解、定时任务方法上增加@Async，进行定时任务调度配置）

3、集成拦截器

	第一步 ：编写拦截器(实现HandlerInterceptor)
	
	第二步：编写拦截器配置类（集成WebMvcConfigurerAdapter），类上增加@Configuration,重写
	addInterceptors方法设置拦截器拦截内容