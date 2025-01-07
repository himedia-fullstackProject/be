package com.example.dailyhub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoriesResponse {
    private final List<MainCategoryDTO> mainCategories; // MainCategoryDTO 리스트
    private final List<SubCategoryDTO> subCategories; // SubCategoryDTO 리스트
}
