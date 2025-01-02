package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.entity.Likes;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
  boolean existsByUserAndPost(User user, Post post);

  Optional<Likes> findByPostAndUser(Post post, User user);


  Page<Likes> findByUser(User user, Pageable pageable);
//유저아이디로 likes 엔티티 조회
}
