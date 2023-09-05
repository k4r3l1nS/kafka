package io.dtechs.producer.scheduled;

import io.dtechs.producer.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final MyService myService;

    @Scheduled(initialDelay = 5000, fixedRate = 1000000)
    public void send10kMessages() {
        myService.send10kMessages();
    }
}
