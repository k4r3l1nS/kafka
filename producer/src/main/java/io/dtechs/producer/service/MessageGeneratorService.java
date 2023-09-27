package io.dtechs.producer.service;

import io.dtechs.producer.kafka.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static io.dtechs.producer.service.units.MessageGenerationUnit.generateMessages;

@Service
@RequiredArgsConstructor
public class MessageGeneratorService {

    private final KafkaSender kafkaSender;

    @Value("${kafka.topic.common}")
    private String COMMON_TOPIC;

    /**
     * Количество отправляемых сообщений за 1 вызов метода sendMessages()
     */
    private static final int NUMBER_OF_MESSAGES = 2000000;

    /**
     * Количество сообщений, содержимых в 1 "порции". Необходимо
     * для обхода ошибки, связанной с недостатком heap memory JVM
     */
    private static final int NUMBER_OF_MESSAGES_IN_BATCH = 20;

    /**
     * Количество потоков в случае необходимости быстрой
     * отправки сообщений
     */
    private static final int NUMBER_OF_THREADS = 20;

    /**
     * Отправка сообщений
     *
     * @param isMultithread Многопоточность отправки
     * @param isSized Файлы отправляются целиком, обходя хранилище,
     *                или лишь временные файлы
     */
    public void sendMessages(boolean isMultithread, boolean isSized) {

        boolean inBatch = isSized;
        if (isMultithread) {
            sendMessagesMultithread(NUMBER_OF_MESSAGES, isSized, inBatch);
        } else {
            sendMessagesNonMultithread(NUMBER_OF_MESSAGES, isSized, inBatch);
        }
    }

    /**
     * Отправка сообщений в несколько потоков
     *
     * @param messageCount Количество сообщений
     * @param isSized Файлы отправляются целиком, обходя хранилище,
     *                или лишь временные файлы
     * @param inBatch Файлы отправляются порционно или сначала
     *                полностью генерируются, а уже потом отправляются
     */
    private void sendMessagesMultithread(int messageCount, boolean isSized, boolean inBatch) {

        var threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        if (!inBatch) {
            try {
                var messageList = generateMessages(messageCount, isSized);
                for (int index = 0; index < messageCount; ++index) {
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
        } else {
            int batchCount = NUMBER_OF_MESSAGES / NUMBER_OF_MESSAGES_IN_BATCH;
            for (int index = 0; index < batchCount; ++index) {
                int finalIndex = index;
                CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("!!! SENDING " + NUMBER_OF_MESSAGES_IN_BATCH * finalIndex + "-" +
                                    NUMBER_OF_MESSAGES_IN_BATCH * (finalIndex + 1) + " MESSAGES !!!");
                            sendMessagesNonMultithread(NUMBER_OF_MESSAGES_IN_BATCH, isSized, false);
                            return null;
                        },
                        threadPool
                );
            }
        }
    }

    /**
     * Отправить сообщения, не прибегая к многопоточности
     *
     * @param messageCount Количество отправляемых сообщений
     * @param inBatch Файлы отправляются порционно или сначала
     *                полностью генерируются, а уже потом отправляются
     */
    private void sendMessagesNonMultithread(int messageCount, boolean isSized, boolean inBatch) {

        var messageList = generateMessages(messageCount, isSized);
        if (!inBatch) {
            try {
                for (int index = 0; index < messageCount; ++index) {
                    var messageDto = messageList.get(index);
                    if (!isSized) {
                        System.out.println("!!! SENDING " + (index + 1) + " MESSAGE !!!");
                    }
                    kafkaSender.sendTransactionalMessage(COMMON_TOPIC, messageDto);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                int batchCount = NUMBER_OF_MESSAGES / NUMBER_OF_MESSAGES_IN_BATCH;
                for (int index = 0; index < batchCount; ++index) {
                    System.out.println("!!! SENDING " + NUMBER_OF_MESSAGES_IN_BATCH * index + "-" +
                            NUMBER_OF_MESSAGES_IN_BATCH * (index + 1) + " MESSAGES !!!");
                    sendMessagesNonMultithread(NUMBER_OF_MESSAGES_IN_BATCH, isSized, false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
