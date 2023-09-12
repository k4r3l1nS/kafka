package io.dtechs.consumer.kafka.listeners;

import io.dtechs.consumer.dto.MessageDto;
import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Component
@RequiredArgsConstructor
@EnableKafka
public class KafkaSpecificListeners {

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
    )
    @Transactional
    public void handlePhoto(MessageDto messageDto) {

        if (messageDto.getId() % 10 == 0)
            throw new RuntimeException("IiIIIiiilIlIIliilIiIIliII");

        File file = messageDto.getFile();

        System.out.println("Фото");
        System.out.println("key = " + file.getName());
        System.out.println("id = " + messageDto.getId() + "\n");

        storageService.saveIfNotExists(photoBucket, file);
    }

    @KafkaListener(
            topics = "${kafka.topic.videos}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void handleVideo(MessageDto messageDto) {

        if (messageDto.getId() % 10 == 0)
            throw new RuntimeException("IiIIIiiilIlIIliilIiIIliII");

        File file = messageDto.getFile();

        System.out.println("Видео");
        System.out.println("key = " + file.getName());
        System.out.println("id = " + messageDto.getId() + "\n");

        storageService.saveIfNotExists(videoBucket, file);
    }

    @KafkaListener(topics = "${kafka.topic.text}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void handleText(MessageDto messageDto) {

        if (messageDto.getId() % 10 == 0)
            throw new RuntimeException("IiIIIiiilIlIIliilIiIIliII");

        File file = messageDto.getFile();

        System.out.println("Текст");
        System.out.println("key = " + file.getName());
        System.out.println("id = " + messageDto.getId() + "\n");

        storageService.saveIfNotExists(textBucket, file);
    }
}
