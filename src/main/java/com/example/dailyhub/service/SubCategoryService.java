package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.SubCategoryDTO;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public List<SubCategoryDTO> getSubCategoriesByMainCategory(Long mainCategoryId) {
        return subCategoryRepository.findBySubCategoryId(mainCategoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SubCategory> findSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    private SubCategoryDTO convertToDTO(SubCategory subCategory) {
        return SubCategoryDTO.builder()
                .id(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .mainCategoryId(subCategory.getMainCategory().getId())
                .build();
    }
}
