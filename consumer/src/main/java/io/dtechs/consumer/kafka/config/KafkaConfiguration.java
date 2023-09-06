package io.dtechs.consumer.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final ProducerFactory<Object, Object> producerFactory;
    private final ConsumerFactory<Object, Object> consumerFactory;

    @Value("${kafka.concurrency}")
    private Integer CONCURRENCY;

    @Value("${kafka.topic.dlq}")
    private String DLQ_TOPIC;

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() { return new KafkaTemplate<>(producerFactory); }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            DefaultErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        kafkaListenerContainerFactory.setCommonErrorHandler(errorHandler);
        kafkaListenerContainerFactory.setConcurrency(CONCURRENCY);

        return kafkaListenerContainerFactory;
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler(
            DeadLetterPublishingRecoverer deadLetterPublishingRecoverer
    ) {
        final var handler = new DefaultErrorHandler(deadLetterPublishingRecoverer);
        handler.addNotRetryableExceptions(Exception.class);
        return handler;
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(
            KafkaTemplate<Object, Object> bytesTemplate
    ) {
        return new DeadLetterPublishingRecoverer(bytesTemplate, ((consumerRecord, ex) ->
                new TopicPartition(DLQ_TOPIC, consumerRecord.partition())));
    }
}
