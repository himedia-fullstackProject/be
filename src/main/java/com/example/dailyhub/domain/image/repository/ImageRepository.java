package com.example.dailyhub.domain.image.repository;

import com.example.dailyhub.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
