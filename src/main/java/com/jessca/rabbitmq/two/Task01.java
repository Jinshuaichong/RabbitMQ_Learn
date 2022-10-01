package com.jessca.rabbitmq.two;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 生产者 发送大量消息
 */
public class Task01 {
    public static final String QUEUE_NAME="hello";
    
    //发送大量消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台中接收信息
        int j=0;
        while(j<100) {
            String message = null;
            int i=0;
            for (i=0; i <= 100; i++) {
                message = "这是第" + i + "条消息";
                System.out.println("发送消息完成：" + message);
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            }
            ++j;
            
            
        }
        
    }
}
