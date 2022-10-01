package com.jessca.rabbitmq.six;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 */
public class ReceiveLogsDirect2 {
    
    public static final String EXCHANGE_NAME="direct_logs";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列 临时队列 队列的名称是随机的 当消费者断开与队列的链接的时候队列就自动删除
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk",EXCHANGE_NAME,"error");
    
        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("ReceiveLogsDirect2控制台打印接收到的消息："+new String(message.getBody(),"UTF-8"));
        };
        
        channel.basicConsume("disk",deliverCallback,consumerTag->{});
    }
}
