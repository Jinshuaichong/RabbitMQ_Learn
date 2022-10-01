package com.jessca.rabbitmq.four;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 验证发布确认模式
 *  1 单个确认模式
 *  2 批量确认模式
 *  3 异步确认模式
 *  用消耗的时间比较那种方式最好
 */
public class ConfirmMessage {
    //批量发消息的个数
    public static final int MESSAGE_COUNT=1000;
    public static void main(String[] args) throws Exception {
        //1 单个确认 耗时591ms
        //ConfirmMessage.publishMessageIndividually();
        //2 批量确认 耗时56ms
        //ConfirmMessage.publishMessageBatch();
        //3 异步批量确认 41ms
        ConfirmMessage.publishMessageAsync();
        
    }
    
    
    //1 单个确认
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            //单个确认 已经发布就马上确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个单独确认消息，耗时"+(end-begin)+"ms");
    
    }
    
    //批量发布确认
    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //批量确认消息大小
        int batchSize=100;
        //批量发送消息 批量发布确认
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            
            //判断达到100条消息的时候 批量确认一次
            if(i%batchSize == 0){
                //发布确认
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个批量确认消息，耗时"+(end-begin)+"ms");
    
    }
    
    //异步确认
    public static void publishMessageAsync() throws Exception{
    
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
       
        //消息确认成功 回调函数
        ConfirmCallback ackCallback=(deliveryTag,multiple)->{
            System.out.println("已确认的消息："+deliveryTag);
        };
        //消息确认失败 回调函数 1 消息的标记 2 是否批量
        ConfirmCallback nackCallback=(deliveryTag,multiple)->{
            System.out.println("未确认的消息："+deliveryTag);
        };
        //准备消息的监听器 监听那些消息成功了 那些消息失败了
        channel.addConfirmListener(ackCallback,nackCallback);
    
        //批量发布消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message="消息"+i;
            channel.basicPublish("",queueName,null,message.getBytes());
        }
        
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个异步发布确认消息，耗时"+(end-begin)+"ms");
    }
    
}
