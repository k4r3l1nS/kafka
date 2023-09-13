package io.dtechs.consumer.kafka.listeners.dlq;

import io.dtechs.consumer.dto.MessageDto;
import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@EnableKafka
public class KafkaDlqListeners {

    private final StorageService storageService;

    @Value("${storage.bucket.dlq-prefix}${storage.bucket.photos}")
    private String dlqPhotoBucket;

    @Value("${storage.bucket.dlq-prefix}${storage.bucket.videos}")
    private String dlqVideoBucket;

    @Value("${storage.bucket.dlq-prefix}${storage.bucket.text}")
    private String dlqTextBucket;

    @KafkaListener(
            topics = "${kafka.topic.dlq-prefix}${kafka.topic.photos}"
    )
    @Transactional
    public void dlqPhotoHandler(MessageDto messageDto) {

        System.out.println("----- DLQ ELEMENT -----\n");
        messageDto.printInfo("dlq PHOTO bucket");
        System.out.println("----- DLQ ELEMENT -----\n\n");

        storageService.saveIfNotExists(dlqPhotoBucket, messageDto.getFile());
    }

    @KafkaListener(
            topics = "${kafka.topic.dlq-prefix}${kafka.topic.videos}"
    )
    @Transactional
    public void dlqVideoHandler(MessageDto messageDto) {

        System.out.println("----- DLQ ELEMENT -----\n");
        messageDto.printInfo("dlq VIDEO bucket");
        System.out.println("----- DLQ ELEMENT -----\n\n");

        storageService.saveIfNotExists(dlqVideoBucket, messageDto.getFile());
    }

    @KafkaListener(
            topics = "${kafka.topic.dlq-prefix}${kafka.topic.text}"
    )
    @Transactional
    public void dlqTextHandler(MessageDto messageDto) {

        System.out.println("----- DLQ ELEMENT -----\n");
        messageDto.printInfo("dlq TEXT bucket");
        System.out.println("----- DLQ ELEMENT -----\n\n");

        storageService.saveIfNotExists(dlqTextBucket, messageDto.getFile());
    }
}
