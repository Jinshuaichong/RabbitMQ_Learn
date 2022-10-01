package com.jessca.rabbitmq.three;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 消息在手动应答时不丢失 放回队列中重新消费
 *
 */
public class Task2 {
    public static final String TASK_QUEUE_NAME="ack_queue";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //开启发布确认 方式有单个确认 批量确认 异步确认
        channel.confirmSelect();
        boolean durable=true;
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        
        //从控制台输入信息
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String  message= scanner.next();
            //设置生产者发送的消息为持久化消息（保存到磁盘上）
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
            System.out.println("生产者发出消息："+message);
        }
    }
}
