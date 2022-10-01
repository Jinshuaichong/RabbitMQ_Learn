package com.jessca.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 */
public class RabbitMqUtils {
	public static Channel getChannel() throws IOException, TimeoutException {
		//创建连接工厂
		ConnectionFactory factory= new ConnectionFactory();
		factory.setHost("192.168.66.129");
		factory.setUsername("admin");
		factory.setPassword("admin");

		Connection connection = factory.newConnection();
		return connection.createChannel();
	}
}
