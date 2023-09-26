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
public class KafkaListeners {

    private final TopicSorterService topicSorterService;
    private final CompatibilityChecker compatibilityChecker;

    /**
     * Обрабатываем объект, попавший в топик, указаный в аннотации @KafkaListener.
     * Можно применять любую логику. В данном случае выбрасываем исключение, если
     * объект "битый", отпрявляя его в dead letter queue. Если исключение не выброшено,
     * перенаправляем сообщение в нужный топик.
     *
     * @param messageDto Обрабатываемое сообщение
     */
    @KafkaListener(
            topics = "${kafka.topic.common}"
    )
    @Transactional
    public void handleObject(MessageDto messageDto) {
        compatibilityChecker.throwIfNotCompatible(messageDto);
        topicSorterService.sendToSortedTopic(messageDto);
    }
}
