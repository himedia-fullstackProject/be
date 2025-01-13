package com.example.dailyhub.domain.post.dto;

import com.example.dailyhub.domain.category.entity.MainCategory;
import com.example.dailyhub.domain.category.entity.SubCategory;
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
    private Long mainCategoryId;
    private Long subCategoryId;
    private Long userId;
    private String userNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ImageDTO image;

}
