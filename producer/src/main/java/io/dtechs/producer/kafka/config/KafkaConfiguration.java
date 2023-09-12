package io.dtechs.producer.kafka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableKafka
public class KafkaConfiguration {

    private final ProducerFactory<Object, Object> producerFactory;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String THIS_PRODUCER_TRANSACTION_ID_PREFIX;

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() {

        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        kafkaTemplate.setTransactionIdPrefix(THIS_PRODUCER_TRANSACTION_ID_PREFIX);

        return kafkaTemplate;
    }

    @Bean
    public KafkaTransactionManager<Object, Object> kafkaTransactionManager() {
        var kafkaTransactionManager = new KafkaTransactionManager<>(producerFactory);
        kafkaTransactionManager.setTransactionSynchronization(
                        AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION
        );
        return kafkaTransactionManager;
    }
}
