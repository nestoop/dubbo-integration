package com.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by botter
 *
 * @Date 15/11/16.
 * @description
 */
@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaProducer kafkaProducer() {
       return null;
    }
}
