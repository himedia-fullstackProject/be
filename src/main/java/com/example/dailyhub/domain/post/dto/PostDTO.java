package com.example.dailyhub.domain.post.dto;

import com.example.dailyhub.domain.image.dto.ImageDTO;
import com.example.dailyhub.domain.likes.dto.LikesDTO;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private List<String> tags;
    private Long mainCategoryId; // Main Category ID
    private Long subCategoryId; // Sub Category ID
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ImageDTO image;

}
