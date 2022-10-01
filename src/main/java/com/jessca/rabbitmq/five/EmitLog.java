package com.jessca.rabbitmq.five;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 生成消息发给交换机
 */
public class EmitLog {
    public static final String EXCHANGE_NAME="logs";
    
    public static void main(String[] args) throws IOException, TimeoutException {
    
        Channel channel = RabbitMqUtils.getChannel();
        
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("生产者发出消息："+message);
        }
    
    
    }
}
