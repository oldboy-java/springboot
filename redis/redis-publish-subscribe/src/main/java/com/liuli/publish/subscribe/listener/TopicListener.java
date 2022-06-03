package com.liuli.publish.subscribe.listener;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;

/**
 * 自定义Topic监听器
 */
public interface TopicListener  extends MessageListener {

    /**
     * 获取topic
     * @return
     */
    Topic getTopic();
}
