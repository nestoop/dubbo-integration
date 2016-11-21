package com.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by botter
 *
 * @Date 15/11/16.
 * @description
 */
@Configuration
public class KafkaProducerConfig {

    private String brokers;

    private String clientId;

    @Autowired
    public KafkaProducerConfig(@Value("${spring.kafka.brokers}") String brokers, @Value("spring.kafka.clientId") String clientId) {
        this.brokers = brokers;
        this.clientId = clientId;
    }

    @Bean
    public KafkaProducer kafkaProducer() {
        return new KafkaProducer<Object, Object>(producerProperties());
    }

    public Map<String, Object> producerProperties() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        propertiesMap.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 10);
        propertiesMap.put(ProducerConfig.ACKS_CONFIG, "all");
        propertiesMap.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        propertiesMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        propertiesMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.example.serialization.KafkaMessageSerializer");
        return propertiesMap;
    }
}
