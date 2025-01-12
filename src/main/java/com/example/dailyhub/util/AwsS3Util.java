package com.example.dailyhub.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Util {

    @Value("${app.props.aws.s3.bucket-name}")
    private String bucket;

    private final AmazonS3 s3Client;

    public String getBucketName() {
        return bucket;
    }

    /**
     * S3에 파일 업로드
     * @param filePaths 파일 경로 리스트
     * @param delFlag 업로드 후 파일 삭제 여부
     */
    public void uploadFiles(List<Path> filePaths, boolean delFlag) {

        if(filePaths == null || filePaths.isEmpty()){
            return;
        }

        for (Path filePath : filePaths) {
            PutObjectRequest request = new PutObjectRequest(bucket, filePath.toFile().getName(), filePath.toFile());
            s3Client.putObject(request);

            if (delFlag) {
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }


    /**
     * S3에서 파일 다운로드
     * @param fileName 파일명
     * @return ResponseEntity<Resource>
     */
    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource("path" + File.separator + fileName);

        if (!resource.exists()) {
            resource = new FileSystemResource("path" + File.separator + "default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }



    /**
     * S3에 파일 삭제
     * @param fileKeys S3 파일 키 리스트
     */
    public void deleteFilesByKeys(List<String> fileKeys) {
        if (fileKeys == null || fileKeys.isEmpty()) {
            return;
        }

        for (String key : fileKeys) {
            s3Client.deleteObject(bucket, key);
        }
    }



}
