package io.dtechs.producer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Builder
public class MessageDto {

    private Long id;
    private File file;
    private Version version;

    public enum Version {V1, V2}
}
