package io.dtechs.sorter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private File file;
    private Version version;

    public void printInfo(String bucketName) {
        System.out.println(bucketName);
        System.out.println("key = " + file.getName());
        System.out.println("id = " + id);
        System.out.println("version = " + version);
        System.out.println();
    }

    public enum Version {V1, V2}
}
