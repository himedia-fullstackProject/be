package com.example.dailyhub.data.dto;

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
//    private List<LikesDTO> likes; // 필요시 LikesDTO를 정의
    private Long mainCategoryId; // 카테고리 ID
    private Long subCategoryId; // 서브카테고리 ID
    private String username; // 작성자 정보
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
