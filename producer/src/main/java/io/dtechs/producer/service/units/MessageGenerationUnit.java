package io.dtechs.producer.service.units;

import io.dtechs.producer.dto.MessageDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MessageGenerationUnit {

    private static final String FILE_DIRECTORY =
            "/home/dtechs/Desktop/project/producer/src/main/resources/file_examples";
    private static final byte[] TEXT_EXAMPLE_BYTE_ARRAY;

    private static final byte[] VIDEO_EXAMPLE_BYTE_ARRAY;

    private static final byte[] PHOTO_EXAMPLE_BYTE_ARRAY;

    static {
        try {
            TEXT_EXAMPLE_BYTE_ARRAY = Files.readAllBytes(Path.of(FILE_DIRECTORY + "/test.txt"));
            VIDEO_EXAMPLE_BYTE_ARRAY = Files.readAllBytes(Path.of(FILE_DIRECTORY + "/test.mp4"));
            PHOTO_EXAMPLE_BYTE_ARRAY = Files.readAllBytes(Path.of(FILE_DIRECTORY + "/test.jpg"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<MessageDto> generateMessages(int messageCount, boolean isSized) {
        return isSized ? generateSizedMessages(messageCount) : generateSimpleMessages(messageCount);
    }
    private static List<MessageDto> generateSizedMessages(int messageCount) {
        List<MessageDto> messageList = new ArrayList<>();
        for (int index = 0; index < messageCount; ++index) {
            String filepath;
            byte[] byteArray;
            switch (Math.abs(ThreadLocalRandom.current().nextInt()) % 3) {
                // 0.5 MB
                case 0 -> {
                    filepath = FILE_DIRECTORY + "/test.mp4";
                    byteArray = VIDEO_EXAMPLE_BYTE_ARRAY;
                }

                // 0.25 MB
                case 1 -> {
                    filepath = FILE_DIRECTORY + "/test.jpg";
                    byteArray = PHOTO_EXAMPLE_BYTE_ARRAY;
                }

                // 0.125 MB
                case 2 -> {
                    filepath = FILE_DIRECTORY + "/test.txt";
                    byteArray = TEXT_EXAMPLE_BYTE_ARRAY;
                }
                // Invalid format
                default -> {
                    filepath = ".err";
                    byteArray = new byte[0];
                }
            }
            File file = new File(filepath);
            messageList.add(
                    MessageDto.builder()
                            .byteArray(byteArray)
                            .file(file)
                            .id(Math.abs(ThreadLocalRandom.current().nextLong()))
                            .version(Math.abs(ThreadLocalRandom.current().nextLong()) % 10 == 0 ?
                                    MessageDto.Version.V2 : MessageDto.Version.V1)
                            .build()
            );
        }
        return messageList;
    }

    private static List<MessageDto> generateSimpleMessages(int messageCount) {

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
}
