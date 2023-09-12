package io.dtechs.consumer;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class ClearStorage {

    @MockBean
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    private AmazonS3 amazonS3;

    @Test
    public void deleteAllFiles() {

        var buckets = amazonS3.listBuckets();

        buckets.forEach(bucket -> {
            var bucketName = bucket.getName();
            var objects = amazonS3.listObjects(bucketName).getObjectSummaries();
            objects.forEach(s3ObjectSummary ->
                    amazonS3.deleteObject(bucketName, s3ObjectSummary.getKey())
            );
        });
    }
}
