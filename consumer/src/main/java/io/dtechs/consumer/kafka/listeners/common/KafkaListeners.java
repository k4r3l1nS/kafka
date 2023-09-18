package io.dtechs.consumer.kafka.listeners.common;

import io.dtechs.consumer.dto.MessageDto;
import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final StorageService storageService;

    @Value("${storage.bucket.photos}")
    private String photoBucket;

    @Value("${storage.bucket.videos}")
    private String videoBucket;

    @Value("${storage.bucket.text}")
    private String textBucket;

    @KafkaListener(
            topics = "${kafka.topic.photos}"
    )
    @Transactional
    public void handlePhoto(MessageDto messageDto) {
        handleFile(messageDto, photoBucket);
    }

    @KafkaListener(
            topics = "${kafka.topic.videos}"
    )
    @Transactional
    public void handleVideo(MessageDto messageDto) {
        handleFile(messageDto, videoBucket);
    }

    @KafkaListener(
            topics = "${kafka.topic.text}"
    )
    @Transactional
    public void handleText(MessageDto messageDto) {
        handleFile(messageDto, textBucket);
    }

    private void handleFile(MessageDto messageDto, String bucketName) {

        if (messageDto.getId() % 10 == 0)
            throw new RuntimeException("IiIIIiiilIlIIliilIiIIliII");

        messageDto.printInfo(bucketName);
        storageService.saveIfNotExists(bucketName, messageDto.getFile());
    }
}
