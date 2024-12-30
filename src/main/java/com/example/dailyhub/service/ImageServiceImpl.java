package com.example.dailyhub.service;

import com.example.dailyhub.data.entity.Image;
import com.example.dailyhub.data.repository.ImageRepository;
import com.example.dailyhub.util.AwsS3Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final AwsS3Util awsS3Util;
  private final ImageRepository imageRepository;

  /**
   * 임시 이미지 업로드
   *
   * @param file 업로드할 이미지 파일
   * @return 저장된 이미지 정보
   */
  @Transactional
  @Override
  public Image uploadTempImage(MultipartFile file) {
    Path tempFilePath = null;
    try {
      // 파일 이름 생성
      String fileName = UUID.randomUUID() + file.getOriginalFilename();
      tempFilePath = Paths.get("temp", fileName);

      // 임시 디렉토리에 파일 저장
      Files.write(tempFilePath, file.getBytes());

      // S3 업로드
      awsS3Util.uploadFiles(List.of(tempFilePath), true);
      String url = "https://s3.amazonaws.com/" + awsS3Util.getBucketName() + "/" + fileName;

      // DB 저장
      Image image = Image.builder()
          .url(url)
          .isTemporary(true)
          .build();
      return imageRepository.save(image);
    } catch (IOException e) {
      throw new RuntimeException("파일 업로드 실패", e);
    } finally {
      // 예외 발생 시에도 임시 파일 삭제
      if (tempFilePath != null && Files.exists(tempFilePath)) {
        try {
          Files.delete(tempFilePath);
        } catch (IOException ex) {
          throw new RuntimeException("임시 파일 삭제 실패", ex);
        }
      }
    }
  }

  /**
   * 임시 이미지 삭제
   *
   * @param imageId 삭제할 이미지 ID
   */
  @Transactional
  @Override
  public void deleteTempImage(Long imageId) {
    Image image = imageRepository.findById(imageId)
        .orElseThrow(() -> new RuntimeException("Image not found"));

    // S3 파일 삭제
    String fileKey = image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1);
    awsS3Util.deleteFilesByKeys(List.of(fileKey));

    // DB 삭제
    imageRepository.delete(image);
  }
}
