package io.dtechs.consumer.checkers;

import io.dtechs.consumer.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CompatibilityChecker {

    @Value("${message.supported-version}")
    private MessageDto.Version supportedVersion;

    public boolean isCompatible(MessageDto messageDto) {
        return messageDto.getVersion().equals(supportedVersion);
    }
}
