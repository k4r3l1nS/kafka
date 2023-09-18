package io.dtechs.sorter.checkers;

import io.dtechs.sorter.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CompatibilityChecker {

    @Value("${kafka.message.supported-version}")
    private MessageDto.Version SUPPORTED_VERSION;

    public boolean isCompatible(MessageDto messageDto) {
        return messageDto.getVersion().equals(SUPPORTED_VERSION);
    }

    public void throwIfNotCompatible(MessageDto messageDto) {
        if (!isCompatible(messageDto)) {
            throw new RuntimeException("Сообщение (id = " +
                    messageDto.getId() + ") не обрабатывается этим сервисом");
        }
    }
}
