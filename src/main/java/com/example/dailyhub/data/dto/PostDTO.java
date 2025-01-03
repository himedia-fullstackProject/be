package com.example.dailyhub.data.dto;

import com.example.dailyhub.data.entity.User;
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
    private String image;
    private String description;
    private String tag1;
    private String tag2;
    private String tag3;
    private List<LikesDTO> likes; // 필요 시 LikesDTO를 정의
    private Long mainCategoryId; // Main Category ID
    private Long subCategoryId; // Sub Category ID
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userNickname;

}
