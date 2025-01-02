package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE "
            + "LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "                        //제목검색
            + "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "                  //내용검색
            + "LOWER(p.user.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "                //작성자검색
            + "LOWER(p.mainCategory.categoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "    //카테고리검색
            + "LOWER(p.subCategory.subCategoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        //서브카테고리검색
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm , Pageable pageable);


    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.tag1) LIKE LOWER(CONCAT('%', :tag, '%')) OR " +
            "LOWER(p.tag2) LIKE LOWER(CONCAT('%', :tag, '%')) OR " +
            "LOWER(p.tag3) LIKE LOWER(CONCAT('%', :tag, '%'))")
    Page<Post> findPostsByHashtags(@Param("tag") String tag , Pageable pageable);
} // 해쉬태그 검색
