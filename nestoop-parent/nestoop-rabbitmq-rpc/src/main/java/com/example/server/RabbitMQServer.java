package com.example.server;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by botter
 *
 * @Date 20/11/16.
 * @description
 */
public class RabbitMQServer {

    private final static String QUEUE_NAME="RPC_QUEUE";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            BasicProperties properties = delivery.getProperties();

            AMQP.BasicProperties repliyProperties =
                    new AMQP.BasicProperties().builder().correlationId(properties.getCorrelationId()).build();

            String message = new String (delivery.getBody());

            System.out.println("server receive message : " + message);

            channel.basicPublish("", properties.getReplyTo(), repliyProperties, "response message".getBytes());

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        }

    }

}
