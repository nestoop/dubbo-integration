package cn.nest.serialization;

import org.apache.kafka.common.serialization.Deserializer;
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
public class KafkaMessageDeSerializer implements Deserializer<Object>{

    public static FSTConfiguration fstConfiguration = FSTConfiguration.createStructConfiguration();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public Object deserialize(String topic, byte[] messageBytes) {
        return fstConfiguration.asObject(messageBytes);
    }

    @Override
    public void close() {

    }
}
