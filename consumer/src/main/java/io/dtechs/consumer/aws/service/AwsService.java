package io.dtechs.consumer.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class AwsService {

    private final AmazonS3 s3Client;

    public boolean fileExists(String bucketName, String key) {

        try {
            return s3Client.doesObjectExist(bucketName, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

//    public List<String> listFolderKeys(String bucketName, String folderPath) {
//
//        var objects = s3Client.listObjects(bucketName, folderPath);
//
//        List<String> keys = new ArrayList<>();
//        objects.getObjectSummaries().forEach(s3ObjectSummary -> {
//            keys.add(s3ObjectSummary.getKey());
//        });
//        return keys;
//    }

//    public URL getFileUrl(String bucketName, String key) {
//
//        try {
//            if (fileExists(bucketName, key)) {
//                return s3Client.getUrl(bucketName, key);
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    public void saveFile(String bucketName, String key, File file) {

        try {
            if (!fileExists(bucketName, key)) {
                s3Client.putObject(bucketName, key, file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
