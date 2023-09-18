package io.dtechs.sorter.kafka.listeners.common;

import io.dtechs.sorter.checkers.CompatibilityChecker;
import io.dtechs.sorter.dto.MessageDto;
import io.dtechs.sorter.service.TopicSorterService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaCommonListeners {

    private final TopicSorterService topicSorterService;
    private final CompatibilityChecker compatibilityChecker;

    @KafkaListener(
            topics = "${kafka.topic.common}"
    )
    @Transactional
    public void handleObject(MessageDto messageDto) {
        compatibilityChecker.throwIfNotCompatible(messageDto);
        topicSorterService.sendToSortedTopic(messageDto);
    }
}
