package com.example.dailyhub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubCategoryDTO {
    private Long id;
    private String subCategoryName;
    private Long mainCategoryId; // 메인 카테고리 ID 추가
}
