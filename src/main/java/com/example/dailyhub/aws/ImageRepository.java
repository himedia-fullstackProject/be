package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.dto.ImageDTO;
import com.example.dailyhub.data.entity.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
