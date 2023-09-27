package io.dtechs.sorter.kafka.listeners.dlq;

import io.dtechs.sorter.dto.MessageDto;
import io.dtechs.sorter.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaDlqListeners {

    private final StorageService storageService;

    @Value("${storage.bucket.dlq}")
    private String DLQ_BUCKET;

    /**
     * Во время стандартной обработки сообщения было выброшено исключение. Поэтому
     * сообщение было перенаправлено в этот обработчик. К нему применяется другая
     * логика. Например, его можно поместить во временное хранилище.
     *
     * @param messageDto Полученное сообщение
     */
    @KafkaListener(
            topics = "${kafka.topic.dlq}"
    )
    @Transactional
    public void handleObject(MessageDto messageDto) {

        System.out.println("----- DLQ ELEMENT -----\n");
        messageDto.printInfo("dlq-invalid");
        System.out.println("----- DLQ ELEMENT -----\n\n");

        storageService.saveIfNotExists(DLQ_BUCKET, messageDto.getFile());
    }
}
