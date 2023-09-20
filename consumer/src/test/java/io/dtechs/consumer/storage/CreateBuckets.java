package io.dtechs.consumer.storage;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CreateBuckets {

    @Autowired
    private AmazonS3 amazonS3;

    @Test
    public void createBuckets() {

        var bucketNames = List.of(
                "common", "photo", "video", "text",
                "dlq-invalid", "dlq-photo", "dlq-video", "dlq-text"
        );

        bucketNames.forEach(bucketName -> {
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                amazonS3.createBucket(bucketName);
            }
        });
    }
}
