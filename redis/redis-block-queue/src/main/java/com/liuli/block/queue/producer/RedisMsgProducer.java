package com.liuli.block.queue.producer;


/**
 * Redis消息生产者
 */
public interface RedisMsgProducer {

    /**
     * 发送消息
     */
     void  send(RedisMqMessage message);

}
