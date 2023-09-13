package io.dtechs.producer.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TopicSorterClient {

    @Value("${topic-sorter.url}")
    private String topicSorterUrl;

    private final RestTemplate restTemplate;

    public String getTopicByFilename(String filename) {


        return restTemplate.getForObject(
                topicSorterUrl + "/get/topic/by-filename?filename={filename}",
                String.class,
                filename
        );
    }

}
