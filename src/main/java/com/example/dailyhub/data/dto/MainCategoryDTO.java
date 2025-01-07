package com.example.dailyhub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MainCategoryDTO {
    private Long id;
    private String categoryName;
    private List<SubCategoryDTO> subCategories; // 서브 카테고리 리스트 추가
}
