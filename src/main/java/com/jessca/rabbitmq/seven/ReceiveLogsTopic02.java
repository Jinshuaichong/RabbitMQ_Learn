package com.jessca.rabbitmq.seven;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 交换机主题模式 消费者
 */
public class ReceiveLogsTopic02 {
    public static final String EXCHANGE_NAME="topic_logs";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        
        String queueName="Q2";
        
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(queueName,EXCHANGE_NAME,"lazy.#");
    
        System.out.println("Topic02等待接收消息");
        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("Topic02控制台打印接收到的消息："+new String(message.getBody(),"UTF-8"));
            System.out.println("接收队列："+queueName+"绑定键："+message.getEnvelope().getRoutingKey());
        };
        
        //接收消息
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
        
    }
}
