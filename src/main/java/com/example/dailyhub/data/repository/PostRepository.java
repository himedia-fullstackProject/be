package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE "
            + "LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(p.user.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(p.mainCategory.categoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
            + "LOWER(p.subCategory.subCategoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Post> searchPosts(@Param("searchTerm") String searchTerm);
}
