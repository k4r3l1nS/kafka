package io.dtechs.sorter.service;

import io.dtechs.sorter.aws.service.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final AwsService awsService;

    public void saveIfNotExists(String bucketName, File file) {
        awsService.saveFile(bucketName, file.getName(), file);
    }
}
