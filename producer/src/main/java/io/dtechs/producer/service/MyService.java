package io.dtechs.producer.service;

import io.dtechs.producer.dto.MessageDto;
import io.dtechs.producer.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MyService {

    private final KafkaSender kafkaSender;

    public void send10kMessages() {

        try {
            for (int messageCount = 0; messageCount < 1000; ++messageCount) {
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

                kafkaSender.sendMessage(topic, MessageDto.builder()
                        .file(file)
                        .build());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
