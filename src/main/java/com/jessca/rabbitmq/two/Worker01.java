package com.jessca.rabbitmq.two;

import com.jessca.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 * @Description 这是一个工作线程
 */
public class Worker01 {
	public static final String QUEUE_NAME="hello";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitMqUtils.getChannel();
		
		DeliverCallback deliverCallback=(consumerTag, message)->{
			System.out.println("C2接收到的消息："+new String(message.getBody()));
		};
		CancelCallback cancelCallback=(consumerTag)->{
			System.out.println("消费消息被中断"+consumerTag);
		};
		System.out.println("C2等待接收消息。。。。");
		channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
	}
	
}
