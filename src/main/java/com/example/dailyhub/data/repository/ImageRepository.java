package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
