package com.example.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by botter
 *
 * @Date 20/11/16.
 * @description
 */
public class RabbitMQClient {

    public final static String REQUEST_QUEUE_NAME = "RPC_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String replyQueueName = channel.queueDeclare().getQueue();

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(REQUEST_QUEUE_NAME, false, queueingConsumer);

        String corretcRatedId = UUID.randomUUID().toString();

        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .correlationId(corretcRatedId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", REQUEST_QUEUE_NAME, properties, "client send message".getBytes());

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corretcRatedId)) {
                String resposeMessage = new String(delivery.getBody());
                System.out.println("client response message : " + resposeMessage);
                break;
            }
        }

        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
