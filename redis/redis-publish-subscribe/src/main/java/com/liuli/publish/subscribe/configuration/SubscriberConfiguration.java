package com.liuli.publish.subscribe.configuration;


import com.liuli.publish.subscribe.listener.TopicListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Map;

/**
 * 配置订阅者信息
 */
@Configuration
public class SubscriberConfiguration  {

    @Autowired
    private ApplicationContext context;

    /**
     * 配置redis消息监听容器
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // 获取所有的消息监听器（订阅者）
        Map<String, TopicListener> beansOfType = context.getBeansOfType(TopicListener.class);

        // 设置监听器，可以设置多个
        for (TopicListener messageListener: beansOfType.values()) {
            container.addMessageListener(new MessageListenerAdapter(messageListener), messageListener.getTopic());
        }
        return container;
    }

}
