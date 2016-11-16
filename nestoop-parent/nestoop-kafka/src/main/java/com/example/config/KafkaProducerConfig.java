package com.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

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
    public KafkaProducerConfig(@Value("${spring.kafka.brokers}") String brokers,@Value("spring.kafka.clientId") String clientId) {
        this.brokers = brokers;
        this.clientId = clientId;
    }

    @Bean
    public KafkaProducer kafkaProducer() {
        return new KafkaProducer<Object, Object>(producerProperties());
    }

    public Properties producerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 10);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.example.serialization.KafkaMessageSerializer");
        return properties;
    }

    @Bean
    public void kafkaProducerContext() {

    }
}
