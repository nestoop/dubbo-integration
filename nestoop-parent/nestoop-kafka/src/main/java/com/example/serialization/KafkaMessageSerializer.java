package com.example.serialization;

import org.apache.kafka.common.serialization.Serializer;
import org.nustaq.serialization.FSTConfiguration;

import java.util.Map;

/**
 * Created by botter
 *
 * @Date 16/11/16.
 * @description
 */
@SuppressWarnings("hiding")
public class KafkaMessageSerializer implements Serializer<Object>{

    public static FSTConfiguration fstConfiguration = FSTConfiguration.createStructConfiguration();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String topic, Object message) {
        if (message == null) {
            return null;
        }
        return fstConfiguration.asByteArray(message);
    }

    @Override
    public void close() {

    }
}
