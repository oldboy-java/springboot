package com.liuli.block.queue.consumer;

/**
 * 定义Redis消息消费者
 */
public interface RedisMsgConsumer {

    /**
     * 消费消息
     */
    public  void  consume();
}
