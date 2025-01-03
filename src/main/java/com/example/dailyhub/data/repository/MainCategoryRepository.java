package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
