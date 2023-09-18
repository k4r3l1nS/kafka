package io.dtechs.sorter.kafka.listeners.dlq;

import io.dtechs.sorter.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KafkaDlqListeners {

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
