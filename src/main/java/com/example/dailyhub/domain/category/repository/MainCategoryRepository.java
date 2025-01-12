package com.example.dailyhub.domain.category.repository;

import com.example.dailyhub.domain.category.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
