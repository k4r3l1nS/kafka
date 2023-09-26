package io.dtechs.sorter.kafka.sender;

import io.dtechs.sorter.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Отправить транзакционное сообщение в указанный топик
     *
     * @param topic Топик для отправки
     * @param messageDto Отправляемое сообщение
     */
    @Transactional
    public void sendTransactionalMessage(String topic, MessageDto messageDto) {
        kafkaTemplate.send(topic, messageDto.getId().toString(), messageDto);
    }

    /**
     * Отправить нетранзакционное сообщение в указанный топик
     *
     * @param topic Топик для отправки
     * @param messageDto Отправляемое сообщение
     */
    public void sendNonTransactionalMessage(String topic, MessageDto messageDto) {
        kafkaTemplate.getProducerFactory().createNonTransactionalProducer().send(
                new ProducerRecord<>(topic, messageDto.getId().toString(), messageDto)
        );
    }
}
