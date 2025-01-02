package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 기존 검색 메서드
    @Query("SELECT p FROM Post p WHERE "
            + "(:searchTerm IS NULL OR :searchTerm = '' OR "
            + "(:searchType = 'title' AND LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) OR "
            + "(:searchType = 'description' AND LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) OR "
            + "(:searchType = 'username' AND LOWER(p.user.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')))) "
            + "AND (p.mainCategory.id = :mainCategoryId OR :mainCategoryId IS NULL) "
            + "AND (p.subCategory.id = :subCategoryId OR :subCategoryId IS NULL)")
    List<Post> searchPosts(@Param("searchTerm") String searchTerm,
                           @Param("mainCategoryId") Long mainCategoryId,
                           @Param("subCategoryId") Long subCategoryId,
                           @Param("searchType") String searchType);

    // 카테고리 조건으로 포스트 조회하는 메서드 추가
    List<Post> findByMainCategoryIdAndSubCategoryId(Long mainCategoryId, Long subCategoryId);
}
