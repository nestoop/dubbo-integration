package cn.nest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/**
 * Created by botter
 *
 * @Date 19/11/16.
 * @description
 */
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    public void sendMessage () {
        Message message = new Message() {
            @Override
            public Object getPayload() {
                return null;
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };

        kafkaTemplate.send(message);
    }
}
