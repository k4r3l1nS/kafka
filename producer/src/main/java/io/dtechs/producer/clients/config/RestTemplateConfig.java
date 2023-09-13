package io.dtechs.producer.clients.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        var restTemplate = new RestTemplateBuilder().build();

        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(300000);
        requestFactory.setConnectTimeout(300000);

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
