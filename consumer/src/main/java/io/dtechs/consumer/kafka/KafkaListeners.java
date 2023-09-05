package io.dtechs.consumer.kafka;

import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

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
    ) public void handlePhoto(ConsumerRecord object) throws IOException {

        Map dtoHashMap = (LinkedHashMap) object.value();

        File file = File.createTempFile(dtoHashMap.get("file").toString().substring(0, 4), ".jpg");

        System.out.println("Обращаемся к чему-нибудь и записываем фото в хранилище");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(photoBucket, file);
    }

    @KafkaListener(
            topics = "${kafka.topic.videos}",
            containerFactory = "kafkaListenerContainerFactory"
    ) public void handleVideo(ConsumerRecord object) throws IOException {

        Map dtoHashMap = (LinkedHashMap) object.value();

        File file = File.createTempFile(dtoHashMap.get("file").toString().substring(0, 4), ".mp4");

        System.out.println("Обращаемся к чему-нибудь и записываем видео в хранилище");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(videoBucket, file);
    }

    @KafkaListener(topics = "${kafka.topic.text}",
            containerFactory = "kafkaListenerContainerFactory"
    ) public void handleText(ConsumerRecord object) throws IOException {

        Map dtoHashMap = (LinkedHashMap) object.value();

        File file = File.createTempFile(dtoHashMap.get("file").toString().substring(0, 4), ".txt");

        System.out.println("Обращаемся к чему-нибудь и записываем текст в хранилище");
        System.out.println("key = " + file.getName() + "\n");

        storageService.saveIfNotExists(textBucket, file);
    }
}
