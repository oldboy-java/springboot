package com.example.demo.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {

    // 默认会存在一个SqlSessionTemplate类型的session:sqlSessionTemplate
    // 自定义用于批处理的session
    @Bean(name = "batchSqlSession")
    public SqlSession batchSqlSession(SqlSessionFactory sessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory, ExecutorType.BATCH);
        return sqlSessionTemplate;
    }
}
