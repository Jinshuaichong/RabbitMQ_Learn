package com.jessca.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jessca
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME="hello";

    /**
     *
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory=new ConnectionFactory();
        //工厂IP连接RabbitMQ的队列
        factory.setHost("192.168.66.129");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("admin");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 1 队列名称
         * 2 队列里面的消息是否持久化（磁盘） 默认情况消息存储在内存中
         * 3 该队列是否只供一个消费者进行消费 是否进行消息共享
         * 4 是否自动删除 最后一个消费者端开链接后 该队列是否自动删除
         * 5 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发消息
        String message="hello world";

        /**
         * 发送一个消费
         * 1 发送到那个交换机
         * 2 路由的Key值是哪个 本次是队列的名称
         * 3 其他参数信息
         * 4 发送消息的消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");



    }

}
