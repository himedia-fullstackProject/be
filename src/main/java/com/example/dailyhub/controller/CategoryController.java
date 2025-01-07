package com.example.dailyhub.controller;

import com.example.dailyhub.data.dto.CategoriesResponse;
import com.example.dailyhub.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<CategoriesResponse> getAllCategories() {
        CategoriesResponse categoriesResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);
    }
}
