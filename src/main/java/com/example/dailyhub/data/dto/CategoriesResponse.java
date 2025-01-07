package com.example.dailyhub.data.dto;

import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoriesResponse {
    private final List<MainCategory> mainCategories;
    private final List<SubCategory> subCategories;
}
