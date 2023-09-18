package io.dtechs.sorter.service;

import io.dtechs.sorter.dto.MessageDto;
import io.dtechs.sorter.kafka.sender.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.dtechs.sorter.parsers.FilenameParser;

@Service
@RequiredArgsConstructor
public class TopicSorterService {

    private final FilenameParser filenameParser;
    private final KafkaSender kafkaSender;

    public void sendToSortedTopic(MessageDto messageDto) {
        try {
            var topic = filenameParser.getTopicByFilename(messageDto.getFile().getName());
            kafkaSender.sendTransactionalMessage(topic, messageDto);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
