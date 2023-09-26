package io.dtechs.sorter.kafka.listeners.dlq;

import io.dtechs.sorter.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KafkaDlqListeners {

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
        messageDto.printInfo("dlq-common");
        System.out.println("----- DLQ ELEMENT -----\n\n");
    }
}
