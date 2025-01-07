package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.MainCategoryDTO;
import com.example.dailyhub.data.dto.SubCategoryDTO;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.repository.MainCategoryRepository;
import com.example.dailyhub.data.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public List<MainCategoryDTO> getAllMainCategories() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();

        return mainCategories.stream()
                .map(mainCategory -> new MainCategoryDTO(
                        mainCategory.getId(),
                        mainCategory.getCategoryName(),
                        mainCategory.getSubCategories().stream()
                                .map(subCategory -> new SubCategoryDTO(
                                        subCategory.getId(),
                                        subCategory.getSubCategoryName(),
                                        mainCategory.getId()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<SubCategoryDTO> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();

        return subCategories.stream()
                .map(subCategory -> new SubCategoryDTO(
                        subCategory.getId(),
                        subCategory.getSubCategoryName(),
                        subCategory.getMainCategory().getId()
                ))
                .collect(Collectors.toList());
    }
}
