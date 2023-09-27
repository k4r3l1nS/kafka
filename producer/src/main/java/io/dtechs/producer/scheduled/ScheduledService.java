package io.dtechs.producer.scheduled;

import io.dtechs.producer.service.MessageGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final MessageGeneratorService messageGeneratorService;

    @Scheduled(initialDelay = 5000, fixedRate = 1000000)
    public void sendMessages() { messageGeneratorService.sendMessages(true, true);}
}