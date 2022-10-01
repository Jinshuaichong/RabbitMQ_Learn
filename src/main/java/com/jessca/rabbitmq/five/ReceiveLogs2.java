package com.jessca.rabbitmq.five;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 消息的接收
 */
public class ReceiveLogs2 {
    
    public static final String EXCHANGE_NAME="logs";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个队列 临时队列 队列的名称是随机的 当消费者断开与队列的链接的时候队列就自动删除
        String queueName=channel.queueDeclare().getQueue();
        /**
         * 绑定交换机与队列
         */
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接收消息，把接收到的消息打印在屏幕上。。。。");
        
        //接收消息回调
        
        //消费者取消消息回调
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("ReceiveLogs2控制台打印接收到的消息："+new String(message.getBody(),"UTF-8"));
        };
        
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
        
    }
}
