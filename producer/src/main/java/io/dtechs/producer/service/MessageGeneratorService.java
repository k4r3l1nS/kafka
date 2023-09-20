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
public class MessageGeneratorService {

    private final KafkaSender kafkaSender;

    @Value("${kafka.topic.common}")
    private String COMMON_TOPIC;

    private static final int NUMBER_OF_MESSAGES = 2000;

    public void sendMessages() {

        try {
            for (int messageCount = 0; messageCount < NUMBER_OF_MESSAGES; ++messageCount) {
                System.out.println("!!! SENDING " + (messageCount + 1) + " MESSAGE !!!");

                String fileName = "file" + messageCount;
                if (messageCount % 3 == 0) {
                    fileName += ".mp4";
                } else if (messageCount % 2 == 0) {
                    fileName += ".jpg";
                } else {
                    fileName += ".txt";
                }
                File file = File.createTempFile("file" + messageCount + "_",
                        fileName.substring(fileName.length() - 4));

                var messageDto = MessageDto.builder()
                        .file(file)
                        .id(Math.abs(ThreadLocalRandom.current().nextLong()))
                        .version(Math.abs(ThreadLocalRandom.current().nextLong()) % 10 == 0 ?
                                MessageDto.Version.V2 : MessageDto.Version.V1)
//                        .version(MessageDto.Version.V1)
                        .build();

                kafkaSender.sendTransactionalMessage(COMMON_TOPIC, messageDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
