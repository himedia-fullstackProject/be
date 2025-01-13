package com.example.dailyhub.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainCategoryDTO {
    private Long id;
    private String categoryName;
    private List<SubCategoryDTO> subCategories; // 서브 카테고리 리스트 추가
}
