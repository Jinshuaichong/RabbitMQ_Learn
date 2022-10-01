package com.jessca.rabbitmq.six;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 生成消息发给交换机 扇出交换机和RoutingKey无关
 */
public class DirectLogs {
    public static final String EXCHANGE_NAME="direct_logs";
    
    public static void main(String[] args) throws IOException, TimeoutException {
    
        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"warning",null,message.getBytes());
            System.out.println("生产者发出消息："+message);
        }
    }
}
