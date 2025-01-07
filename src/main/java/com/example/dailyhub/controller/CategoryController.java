package com.example.dailyhub.controller;

import com.example.dailyhub.data.dto.MainCategoryDTO;
import com.example.dailyhub.data.dto.SubCategoryDTO;
import com.example.dailyhub.service.CategoryService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PermitAll
    @GetMapping("/api/main-categories")
    public List<MainCategoryDTO> getAllMainCategories() {
        return categoryService.getAllMainCategories(); // 메인 카테고리 가져오기
    }

    @PermitAll
    @GetMapping("/api/sub-categories")
    public List<SubCategoryDTO> getAllSubCategories() {
        return categoryService.getAllSubCategories(); // 서브 카테고리 가져오기
    }
}
