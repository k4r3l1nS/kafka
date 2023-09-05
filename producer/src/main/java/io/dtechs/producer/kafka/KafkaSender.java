package io.dtechs.producer.kafka;

import io.dtechs.producer.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String topic, MessageDto messageDto) {
        kafkaTemplate.send(topic, messageDto);
    }
}
