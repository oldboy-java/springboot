package com.liuli.block.queue.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisMqMessage<T> {
    /**
     * 队列名称
     */
    private  String queue;

    /**
     * 发送数据
     */
    private T data;
}
