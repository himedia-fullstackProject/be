package com.example.dailyhub.domain.post.repository;

import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.domain.post.repository.querydsl.PostRepositoryCustom;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

  Page<Post> findAll(Pageable pageable);
}
