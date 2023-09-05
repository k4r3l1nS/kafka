package io.dtechs.producer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Builder
public class MessageDto {

    private File file;
}
