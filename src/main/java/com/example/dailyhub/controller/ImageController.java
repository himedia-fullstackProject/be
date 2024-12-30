package com.example.dailyhub.controller;

import com.example.dailyhub.data.entity.Image;
import com.example.dailyhub.data.repository.ImageRepository;
import com.example.dailyhub.service.ImageService;
import com.example.dailyhub.util.AwsS3Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  // 임시 이미지 업로드
  @PostMapping("/temp")
  public ResponseEntity<Image> uploadTempImage(@RequestParam("file") MultipartFile file) {
    Image image = imageService.uploadTempImage(file);
    return ResponseEntity.ok(image);
  }

  // 임시 이미지 삭제
  @DeleteMapping("/temp/{imageId}")
  public ResponseEntity<Void> deleteTempImage(@PathVariable Long imageId) {
    imageService.deleteTempImage(imageId);
    return ResponseEntity.ok().build();
  }

}
