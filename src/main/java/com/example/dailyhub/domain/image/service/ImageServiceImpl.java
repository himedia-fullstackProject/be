package com.example.dailyhub.domain.image.service;

import com.example.dailyhub.domain.image.entity.Image;
import com.example.dailyhub.domain.image.repository.ImageRepository;
import com.example.dailyhub.util.AwsS3Util;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final AwsS3Util awsS3Util;
  private final ImageRepository imageRepository;

  /**
   * 임시 이미지 업로드
   * @param file 업로드할 이미지 파일
   * @return 저장된 이미지 정보
   */
  @Transactional
  @Override
  public Image uploadTempImage(MultipartFile file) {
    Path tempFilePath = null;
    try {
      // 파일 저장 경로 확인 및 생성
      Path tempDir = Paths.get("temp");
      if (!Files.exists(tempDir)) {
        Files.createDirectories(tempDir); // 폴더가 없으면 생성
        log.info("임시 폴더 생성: {}", tempDir.toAbsolutePath());
      }

      // 파일 이름 생성
      String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
      tempFilePath = Paths.get("temp", fileName);

      // 임시 디렉토리에 파일 저장
      Files.write(tempFilePath, file.getBytes());

      // S3 업로드
      awsS3Util.uploadFiles(List.of(tempFilePath), true);
      String url = awsS3Util.getFileUrl(fileName);

      // DB 저장
      Image image = Image.builder()
          .url(url)
          .isTemporary(true)
          .post(null)
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
          log.warn("임시 파일 삭제 실패: {}", tempFilePath, ex);
        }
      }
    }
  }

  /**
   * 여러 임시 이미지 업로드
   * @param files 업로드할 이미지 파일 리스트
   * @return 저장된 이미지 정보 리스트
   */
  @Transactional
  public List<Image> uploadTempImages(List<MultipartFile> files) {
    List<Image> images = new ArrayList<>();

    for (MultipartFile file : files) {
      Image image = uploadTempImage(file); // 개별 이미지 업로드 처리
      images.add(image);
    }

    return images;
  }

  /**
   * 임시 이미지 삭제
   * @param imageId 삭제할 이미지 ID
   */
  @Transactional
  @Override
  public void deleteTempImage(Long imageId) {
    Image image = imageRepository.findById(imageId)
        .orElseThrow(() -> new RuntimeException("Image not found"));

    // URL 디코딩을 통해 정확한 파일 키 추출
    String encodedFileKey = image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1);
    String decodedFileKey = URLDecoder.decode(encodedFileKey, StandardCharsets.UTF_8);
    log.info("Decoded file key: {}", decodedFileKey);

    // S3 파일 삭제
    awsS3Util.deleteFilesByKeys(List.of(decodedFileKey));

    // DB 삭제
    imageRepository.delete(image);
  }

  /**
   * 여러 임시 이미지 삭제
   * @param imageIds 삭제할 이미지 ID 리스트
   */
  @Transactional
  public void deleteTempImages(List<Long> imageIds) {
    List<Image> images = imageRepository.findAllById(imageIds);

    List<String> fileKeys = new ArrayList<>();
    for (Image image : images) {
      String fileKey = image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1);
      fileKeys.add(fileKey);
    }

    // S3와 DB에서 삭제
    awsS3Util.deleteFilesByKeys(fileKeys);
    imageRepository.deleteAll(images);
  }
}
