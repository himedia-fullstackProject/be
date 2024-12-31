package com.example.dailyhub.controller;

import com.example.dailyhub.data.entity.Image;
import com.example.dailyhub.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  /**
   * 여러 임시 이미지 업로드
   * @param files 업로드할 이미지 파일 리스트
   * @return 저장된 이미지 정보 리스트
   */
  @PostMapping("/temp")
  public ResponseEntity<List<Image>> uploadTempImages(@RequestParam("files") List<MultipartFile> files) {
    List<Image> images = imageService.uploadTempImages(files);
    return ResponseEntity.ok(images);
  }

  /**
   * 단일 임시 이미지 삭제
   * @param imageId 삭제할 이미지 ID
   */
  @DeleteMapping("/temp/{imageId}")
  public ResponseEntity<Void> deleteTempImage(@PathVariable Long imageId) {
    imageService.deleteTempImage(imageId);
    return ResponseEntity.ok().build();
  }

  /**
   * 여러 임시 이미지 삭제
   * @param imageIds 삭제할 이미지 ID 리스트
   */
  @DeleteMapping("/temp")
  public ResponseEntity<Void> deleteTempImages(@RequestBody List<Long> imageIds) {
    imageService.deleteTempImages(imageIds);
    return ResponseEntity.ok().build();
  }
}
