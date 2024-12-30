package com.example.dailyhub.controller;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class AwsTestController {

  private final AmazonS3 s3Client;

  @GetMapping("/aws-connection")
  public String testAwsConnection() {
    try {
      // 버킷 리스트를 가져와봄으로써 연결 테스트
      s3Client.listBuckets();
      log.info("AWS S3 연결 성공!");
      return "AWS S3 연결 성공!";
    } catch (Exception e) {
      log.error("AWS S3 연결 실패: ", e);
      return "AWS S3 연결 실패: " + e.getMessage();
    }
  }
}
