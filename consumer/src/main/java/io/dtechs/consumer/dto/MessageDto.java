package io.dtechs.consumer.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class MessageDto {

    private File file;

    public MessageDto() {}
}
