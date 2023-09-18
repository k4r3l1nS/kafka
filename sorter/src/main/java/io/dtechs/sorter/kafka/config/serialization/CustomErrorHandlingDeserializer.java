package io.dtechs.sorter.kafka.config.serialization;

import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

public class CustomErrorHandlingDeserializer<T> extends ErrorHandlingDeserializer<T> {

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {

        headers.remove("__TypeId__");
        return super.deserialize(topic, headers, data);
    }
}
