package com.example.dailyhub.service;

import com.example.dailyhub.data.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  Image uploadTempImage(MultipartFile file);

  void deleteTempImage(Long imageId);
}
