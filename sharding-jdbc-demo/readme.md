执行流程
  SQL解析-》SQL路由-》SQL改写-》SQL执行-》归并结果



1、水平分表 （sharding-jdbc-quickstart）
#定义分片规则配置
##定义数据源
spring.shardingsphere.datasource.names=m1
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/sharding_jdbc_order
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=123456


#指定t_order表的数据分布情况，配置数据节点m1.t_order_1,m1.t_order_2
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=m1.t_order_$->{1..2}

#指定t_order的主键生成策略（SNOWFLAKE）和主键列
spring.shardingsphere.sharding.tables.t_order.key-generator.column=f_id
#SnowflakeShardingKeyGenerator类中指定雪花算法名称SNOWFLAKE
#spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

#使用自定义Redis主键算法
spring.shardingsphere.sharding.tables.t_order.key-generator.type=REDIS

#定义生成器使用的属性信息
spring.shardingsphere.sharding.tables.t_order.key-generator.props.host=localhost
spring.shardingsphere.sharding.tables.t_order.key-generator.props.port=6379

#指定t_order分片策略,包括分片键和分表算法
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=f_id
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order_$->{f_id %2  + 1}


2、sharding-jdbc-split-db-split-table（水平分库、水平分表）
#定义分片规则配置
##定义数据源
spring.shardingsphere.datasource.names=m1,m2
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/db_order_1
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=123456

spring.shardingsphere.datasource.m2.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m2.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m2.url=jdbc:mysql://localhost:3306/db_order_2
spring.shardingsphere.datasource.m2.username=root
spring.shardingsphere.datasource.m2.password=123456

#分库策略
#所有表的默认水平分库策略
#spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=f_user_id
#spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=m$->{f_user_id %2  + 1}

#指定单个表水平分库切分策略
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.sharding-column=f_user_id
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.algorithm-expression=m$->{f_user_id %2  + 1}


#指定t_order表的数据分布情况，配置数据节点m1.t_order_1,m1.t_order_2,m2.t_order_1,m2.t_order_2
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=m$->{1..2}.t_order_$->{1..2}


#指定t_order的主键生成策略（SNOWFLAKE）和主键列
spring.shardingsphere.sharding.tables.t_order.key-generator.column=f_id
#SnowflakeShardingKeyGenerator类中指定雪花算法名称SNOWFLAKE
#spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

#使用自定义Redis主键算法
spring.shardingsphere.sharding.tables.t_order.key-generator.type=REDIS

#定义生成器使用的属性信息
spring.shardingsphere.sharding.tables.t_order.key-generator.props.host=localhost
spring.shardingsphere.sharding.tables.t_order.key-generator.props.port=6379



#指定t_order分片策略,包括分片键和分表算法
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=f_id
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order_$->{f_id %2  + 1}


#打开sql输出
spring.shardingsphere.props.sql.show = true


3、垂直分库（数据库设计时已经规划好）
#定义分片规则配置
##定义数据源
spring.shardingsphere.datasource.names=m0
spring.shardingsphere.datasource.m0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m0.url=jdbc:mysql://localhost:3306/db_user
spring.shardingsphere.datasource.m0.username=root
spring.shardingsphere.datasource.m0.password=123456


#指定t_user表的数据分布情况
spring.shardingsphere.sharding.tables.t_user.actual-data-nodes=m$->{0}.t_user


#指定t_user的主键生成策略（SNOWFLAKE）和主键列
spring.shardingsphere.sharding.tables.t_user.key-generator.column=f_id
#SnowflakeShardingKeyGenerator类中指定雪花算法名称SNOWFLAKE
spring.shardingsphere.sharding.tables.t_user.key-generator.type=SNOWFLAKE


#指定t_user分片策略,包括分片键和分表算法
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.sharding-column=f_id
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.algorithm-expression=t_user

#打开sql输出
spring.shardingsphere.props.sql.show = true


4、公共表
  在多个数据库中db_order_1和db_order_2中分表添加公共表

#定义分片规则配置
##定义数据源
spring.shardingsphere.datasource.names=m1,m2
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/db_order_1
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=123456

spring.shardingsphere.datasource.m2.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m2.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.m2.url=jdbc:mysql://localhost:3306/db_order_2
spring.shardingsphere.datasource.m2.username=root
spring.shardingsphere.datasource.m2.password=123456

#分库策略
#所有表的默认水平分库策略
#spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=f_user_id
#spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=m$->{f_user_id %2  + 1}

#指定单个表水平分库切分策略
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.sharding-column=f_user_id
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.algorithm-expression=m$->{f_user_id %2  + 1}


#指定t_order表的数据分布情况，配置数据节点m1.t_order_1,m1.t_order_2,m2.t_order_1,m2.t_order_2
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=m$->{1..2}.t_order_$->{1..2}


#指定t_order的主键生成策略（SNOWFLAKE）和主键列
spring.shardingsphere.sharding.tables.t_order.key-generator.column=f_id
#SnowflakeShardingKeyGenerator类中指定雪花算法名称SNOWFLAKE
#spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

#使用自定义Redis主键算法
spring.shardingsphere.sharding.tables.t_order.key-generator.type=REDIS

#定义生成器使用的属性信息
spring.shardingsphere.sharding.tables.t_order.key-generator.props.host=localhost
spring.shardingsphere.sharding.tables.t_order.key-generator.props.port=6379



#指定t_order分片策略,包括分片键和分表算法
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=f_id
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order_$->{f_id %2  + 1}



#配置公共表
spring.shardingsphere.sharding.broadcast-tables=t_dict
spring.shardingsphere.sharding.tables.t_dict.key-generator.column=f_id
#SnowflakeShardingKeyGenerator类中指定雪花算法名称SNOWFLAKE
spring.shardingsphere.sharding.tables.t_dict.key-generator.type=SNOWFLAKE


#打开sql输出
spring.shardingsphere.props.sql.show = true
