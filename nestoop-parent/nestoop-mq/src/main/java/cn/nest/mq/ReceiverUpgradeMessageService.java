package cn.nest.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by botter
 *
 * @Date 11/11/16.
 * @description
 */
@Service
@RabbitListener(queues = RabbitMQConfig.MQ_UPGRADE_QUEUE)
public class ReceiverUpgradeMessageService {


    @RabbitHandler
    public void receiverUpgradeMessage(Object message) {
        Message upgradeMessage = (Message) message;
        System.out.println("upgrade message :" + new String(upgradeMessage.getBody()));

    }
}
