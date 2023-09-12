package io.dtechs.consumer.kafka.config.serialization;

import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

public class CustomErrorHandlingDeserializer<T> extends ErrorHandlingDeserializer<T> {

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        return super.deserialize(topic, data);
    }
}
