package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 포스트 검색 메서드 (페이지네이션 포함)
    @Query("SELECT p FROM Post p WHERE " +
            "(:searchTerm IS NULL OR :searchTerm = '' OR " +
            "p.title LIKE CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> searchPosts(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    // 카테고리 조건으로 포스트 조회하는 메서드 추가
    List<Post> findByMainCategoryIdAndSubCategoryId(Long mainCategoryId, Long subCategoryId);

    // 포스트 ID로 User 정보와 함께 조회하는 메서드 추가
    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);

    // 사용자 ID로 포스트 조회하는 메서드 추가
    List<Post> findByUserId(Long userId);

    // 해시태그 검색 메서드
    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN FETCH p.user u " +
            "LEFT JOIN FETCH p.mainCategory mc "+
            "LEFT JOIN FETCH p.subCategory sc WHERE "+
            "LOWER(p.tag1) LIKE LOWER(CONCAT('%', :tag, '%')) OR " +
            "LOWER(p.tag2) LIKE LOWER(CONCAT('%', :tag, '%')) OR " +
            "LOWER(p.tag3) LIKE LOWER(CONCAT('%', :tag, '%'))")
    Page<Post> findPostsByHashtags(@Param("tag") String tag, Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> getAllPostByUserId(@Param("user_id") Long userId, Pageable pageable);

}
