package io.dtechs.sorter.parsers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilenameParser {

    @Value("${kafka.topic.photos}")
    private String photoTopic;

    @Value("${kafka.topic.videos}")
    private String videoTopic;

    @Value("${kafka.topic.text}")
    private String textTopic;

    /**
     * Возвращает название топика по расширению файла
     *
     * @param filename Название файла
     * @return Топик
     */
    public String getTopicByFilename(String filename) {

        int dotIndex = filename.indexOf('.');
        var suffix = filename.substring(dotIndex);

        String topic = switch (suffix) {
            case ".jpg" -> photoTopic;
            case ".mp4" -> videoTopic;
            case ".txt" -> textTopic;
            default -> null;
        };

        if (topic == null) {
            throw new RuntimeException("Некорректное название файла: " + filename);
        }

        return topic;
    }
}
