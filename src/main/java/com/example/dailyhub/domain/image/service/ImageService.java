package com.example.dailyhub.domain.image.service;

import com.example.dailyhub.domain.image.entity.Image;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  Image uploadTempImage(MultipartFile file);

  void deleteTempImage(Long imageId);

  void deleteTempImages(List<Long> imageIds);

  List<Image> uploadTempImages(List<MultipartFile> files);
}
