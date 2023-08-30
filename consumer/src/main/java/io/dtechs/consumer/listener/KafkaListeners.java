package io.dtechs.consumer.listener;

import io.dtechs.consumer.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    @KafkaListener(topics = "${kafka.topics.photos}")
    public void handlePhoto(MessageDto messageDto) {

        System.out.println("Обращаемся к чему-нибудь и записываем фото в хранилище");
        System.out.println("key = " + messageDto.getKey() + "\n");
    }

    @KafkaListener(topics = "${kafka.topics.videos}", containerFactory = "kafkaListenerContainerFactory")
    public void handleVideo(MessageDto messageDto) {

        System.out.println("Обращаемся к чему-нибудь и записываем видео в хранилище");
        System.out.println("key = " + messageDto.getKey() + "\n");
    }

    @KafkaListener(topics = "${kafka.topics.text}")
    public void handleText(MessageDto messageDto) {

        System.out.println("Обращаемся к чему-нибудь и записываем текст в хранилище");
        System.out.println("key = " + messageDto.getKey() + "\n");
    }
}
