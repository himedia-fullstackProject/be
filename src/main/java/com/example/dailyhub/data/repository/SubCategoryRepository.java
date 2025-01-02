package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByMainCategoryId(Long mainCategoryId);
}
