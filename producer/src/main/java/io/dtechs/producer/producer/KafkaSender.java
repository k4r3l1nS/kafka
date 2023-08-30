package io.dtechs.producer.producer;

import io.dtechs.producer.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String topic, MessageDto messageDto) {
        kafkaTemplate.send(topic, String.valueOf(ThreadLocalRandom.current().nextLong()) , messageDto);
    }
}
