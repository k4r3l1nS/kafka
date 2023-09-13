package io.dtechs.producer.service;

import io.dtechs.producer.clients.TopicSorterClient;
import io.dtechs.producer.dto.MessageDto;
import io.dtechs.producer.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MyService {

    private final KafkaSender kafkaSender;
    private final TopicSorterClient topicSorterClient;

    public void send10kMessages() {

        try {
            for (int messageCount = 0; messageCount < 100; ++messageCount) {
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
                        .build();

                try {
                    var topic = topicSorterClient.getTopicByFilename(
                            messageDto.getFile().getName()
                    );
                    kafkaSender.sendMessage(topic, messageDto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
