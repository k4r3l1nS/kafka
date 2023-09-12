package io.dtechs.consumer.kafka.listeners;

import io.dtechs.consumer.parsers.FilenameParser;
import io.dtechs.consumer.dto.MessageDto;
import io.dtechs.consumer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Component
@RequiredArgsConstructor
@EnableKafka
public class KafkaCommonListener {

    private final StorageService storageService;

    private final FilenameParser filenameParser;

    @KafkaListener(
            topics = "${kafka.topic.overall}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void handleFile(MessageDto messageDto) {

        if (messageDto.getId() % 10 == 0)
            throw new RuntimeException("IiIIIiiilIlIIliilIiIIliII");

        File file = messageDto.getFile();

        String bucketName = filenameParser.getBucketName(file);

        System.out.println(bucketName);
        System.out.println("key = " + file.getName());
        System.out.println("id = " + messageDto.getId() + "\n");

        storageService.saveIfNotExists(bucketName, file);
    }
}
