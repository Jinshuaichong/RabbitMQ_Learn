package com.jessca.rabbitmq.three;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.jessca.rabbitmq.util.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * 消息在手动应答时不丢失 放回队列中重新消费
 */
public class Work03 {
    public static final String TASK_QUEUE_NAME="ack_queue";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收消息处理时间较短");
    
        DeliverCallback deliverCallback=(consumerTag, message)->{
            //沉睡1s
            SleepUtils.sleep(1);
            System.out.println("接收到的消息："+new String(message.getBody(),"UTF-8"));
            //手动应答 1 消息的标记tag 2 是否批量应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println(consumerTag+"消费者取消消费接口回调");
        };
        //设置不公平分发 1代表不公平分发  其它值表示预取值
        channel.basicQos(5);
        //采用手动应答
        boolean autoACK=false;
        channel.basicConsume(TASK_QUEUE_NAME,autoACK,deliverCallback,cancelCallback);
    }
}
