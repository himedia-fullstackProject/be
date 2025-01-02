package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.MainCategoryDTO;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public List<MainCategoryDTO> getAllMainCategories() {
        return mainCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MainCategory> findMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    private MainCategoryDTO convertToDTO(MainCategory mainCategory) {
        return MainCategoryDTO.builder()
                .id(mainCategory.getId())
                .categoryName(mainCategory.getCategoryName())
                .build();
    }
}
