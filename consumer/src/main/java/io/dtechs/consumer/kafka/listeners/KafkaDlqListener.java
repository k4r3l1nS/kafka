package io.dtechs.consumer.kafka.listeners;

import io.dtechs.consumer.dto.MessageDto;
import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
@EnableKafka
public class KafkaDlqListener {

    private final StorageService storageService;

    @Value("${storage.bucket.dlq}")
    private String dlqBucket;

    @KafkaListener(
            topics = "${kafka.topic.dlq}"
    ) public void dlqHandler(MessageDto messageDto) {

        File file = messageDto.getFile();

        System.out.println("----- DLQ ELEMENT -----");
        System.out.println("dlq bucket");
        System.out.println("key = " + file.getName());
        System.out.println("id = " + messageDto.getId() + "\n");
        System.out.println("----- DLQ ELEMENT -----\n");

        storageService.saveIfNotExists(dlqBucket, file);
    }
}
