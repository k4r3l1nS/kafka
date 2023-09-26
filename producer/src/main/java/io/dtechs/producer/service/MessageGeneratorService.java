package io.dtechs.producer.service;

import io.dtechs.producer.dto.MessageDto;
import io.dtechs.producer.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MessageGeneratorService {

    private final KafkaSender kafkaSender;

    @Value("${kafka.topic.common}")
    private String COMMON_TOPIC;

    private static final int NUMBER_OF_MESSAGES = 2000000;

    public void sendMessagesMultithread() {

        var threadPool = Executors.newFixedThreadPool(16);
        try {
            var messageList = generateSizedMessages(NUMBER_OF_MESSAGES);
            for (int index = 0; index < NUMBER_OF_MESSAGES; ++index) {
                var messageDto = messageList.get(index);
                int finalIndex = index;
                CompletableFuture.supplyAsync(
                        () -> {
                            kafkaSender.sendTransactionalMessage(COMMON_TOPIC, messageDto);
                            System.out.println("!!! SENDING " + (finalIndex + 1) + " MESSAGE !!!");
                            return null;
                        },
                        threadPool
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessages() {

        try {
            var messageList = generateSizedMessages(NUMBER_OF_MESSAGES);
            for (int index = 0; index < NUMBER_OF_MESSAGES; ++index) {
                System.out.println("!!! SENDING " + (index + 1) + " MESSAGE !!!");
                var messageDto = messageList.get(index);
                kafkaSender.sendTransactionalMessage(COMMON_TOPIC, messageDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<MessageDto> generateSimpleMessages(int messageCount) {
        List<MessageDto> messageList = new ArrayList<>();
        try {
            for (int index = 0; index < messageCount; ++index) {
                String fileName = "file" + index;
                if (index % 3 == 0) {
                    fileName += ".mp4";
                } else if (index % 2 == 0) {
                    fileName += ".jpg";
                } else {
                    fileName += ".txt";
                }
                File file = File.createTempFile("file" + index + "_",
                        fileName.substring(fileName.length() - 4));

                messageList.add(
                        MessageDto.builder()
                                .file(file)
                                .id(Math.abs(ThreadLocalRandom.current().nextLong()))
                                .version(Math.abs(ThreadLocalRandom.current().nextLong()) % 10 == 0 ?
                                        MessageDto.Version.V2 : MessageDto.Version.V1)
                                .build()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return messageList;
    }

    public List<MessageDto> generateSizedMessages(int messageCount) {
        List<MessageDto> messageList = new ArrayList<>();
        for (int index = 0; index < messageCount; ++index) {
            String filepath = "/home/dtechs/Desktop/project/producer/src/main/resources/file_examples";
            filepath +=
                    (switch (Math.abs(ThreadLocalRandom.current().nextInt()) % 3) {
                        case 0 -> "/test.mp4";
                        case 1 -> "/test.jpg";
                        case 2 -> "/test.txt";
                        default -> ".err";
                    });
            File file = new File(filepath);

            messageList.add(
                    MessageDto.builder()
                            .file(file)
                            .id(Math.abs(ThreadLocalRandom.current().nextLong()))
                            .version(Math.abs(ThreadLocalRandom.current().nextLong()) % 10 == 0 ?
                                    MessageDto.Version.V2 : MessageDto.Version.V1)
                    .build()
            );
        }
        return messageList;
    }
}
