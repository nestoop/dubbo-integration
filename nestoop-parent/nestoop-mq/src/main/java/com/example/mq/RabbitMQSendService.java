package com.example.mq;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by botter
 *
 * @Date 11/11/16.
 * @description
 */
@Service
public class RabbitMQSendService {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendAppMessage(Object message) {
        sendMessage(message, RabbitMQConfig.MQ_EXCHANGE_APP_KEY);
    }

    public void sendUpgradeMessage(Object message) {
        sendMessage(message, RabbitMQConfig.MQ_EXCHANGE_UPGRADE_KEY);
    }

    private void sendMessage(Object message, String exchangeKey) {
        rabbitMessagingTemplate.convertAndSend(RabbitMQConfig.MQ_EXCHANGE, exchangeKey, message);
    }

}
