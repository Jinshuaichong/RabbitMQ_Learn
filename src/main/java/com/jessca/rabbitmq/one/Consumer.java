package com.jessca.rabbitmq.one;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 消费者 接收消息
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME="hello";

    /**
     * 接收消息
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel= RabbitMqUtils.getChannel();

        //生命
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("消费消息被中断");
        };
        /**
         * 消费者消费消息
         * 1 消费那个队列
         * 2 消费成功后是否要自动应答
         * 3 消费者未成功消费的回调
         * 4 消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }

}
