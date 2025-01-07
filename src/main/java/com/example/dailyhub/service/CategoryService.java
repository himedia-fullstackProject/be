package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.CategoriesResponse;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.repository.MainCategoryRepository;
import com.example.dailyhub.data.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public CategoriesResponse getAllCategories() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return new CategoriesResponse(mainCategories, subCategories);
    }
}
