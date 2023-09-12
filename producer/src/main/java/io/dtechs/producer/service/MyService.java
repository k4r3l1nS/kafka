package io.dtechs.producer.service;

import io.dtechs.producer.dto.MessageDto;
import io.dtechs.producer.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MyService {

    private final KafkaSender kafkaSender;

    @Value("${kafka.topic.common}")
    private String COMMON_TOPIC;

    public void send10kMessages() {

        try {
            for (int messageCount = 0; messageCount < 100; ++messageCount) {
                System.out.println("!!! SENDING " + (messageCount + 1) + " MESSAGE !!!");

                String fileName = "file" + messageCount;
                String topic;
                if (messageCount % 3 == 0) {
                    topic = "video";
                    fileName += ".mp4";
                } else if (messageCount % 2 == 0) {
                    topic = "photo";
                    fileName += ".jpg";
                } else {
                    topic = "text";
                    fileName += ".txt";
                }
                File file = File.createTempFile("file" + messageCount + "_",
                        fileName.substring(fileName.length() - 4));

                var messageDto = MessageDto.builder()
                                .file(file)
                        .id(Math.abs(ThreadLocalRandom.current().nextLong()))
                        .build();

                kafkaSender.sendMessage(topic, messageDto);
                kafkaSender.sendMessage(COMMON_TOPIC, messageDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
