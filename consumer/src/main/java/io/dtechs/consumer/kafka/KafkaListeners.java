package io.dtechs.consumer.kafka;

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
public class KafkaListeners {

    private final StorageService storageService;

    @Value("${storage.bucket.photos}")
    private String photoBucket;

    @Value("${storage.bucket.videos}")
    private String videoBucket;

    @Value("${storage.bucket.text}")
    private String textBucket;

    @KafkaListener(
            topics = "${kafka.topic.photos}",
            containerFactory = "kafkaListenerContainerFactory"
    ) public void handlePhoto(MessageDto messageDto) {

        File file = messageDto.getFile();

        System.out.println("Фото");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(photoBucket, file);
    }

    @KafkaListener(
            topics = "${kafka.topic.videos}",
            containerFactory = "kafkaListenerContainerFactory"
    ) public void handleVideo(MessageDto messageDto) {

        File file = messageDto.getFile();

        System.out.println("Видео");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(videoBucket, file);
    }

    @KafkaListener(topics = "${kafka.topic.text}",
            containerFactory = "kafkaListenerContainerFactory"
    ) public void handleText(MessageDto messageDto) {

        File file = messageDto.getFile();

        System.out.println("Текст");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(textBucket, file);
    }
}
