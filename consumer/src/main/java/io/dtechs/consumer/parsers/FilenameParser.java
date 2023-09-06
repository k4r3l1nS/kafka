package io.dtechs.consumer.parsers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FilenameParser {

    @Value("${storage.bucket.photos_overall}")
    private String photoBucket;

    @Value("${storage.bucket.videos_overall}")
    private String videoBucket;

    @Value("${storage.bucket.text_overall}")
    private String textBucket;

    public String getBucketName(File file) {

        var filepath = file.toURI().getPath();
        var suffix = filepath.substring(filepath.indexOf('.'));

        return switch (suffix) {
            case ".jpg" -> photoBucket;
            case ".mp4" -> videoBucket;
            case ".txt" -> textBucket;
            default -> throw new RuntimeException("Unsupported type");
        };
    }
}
