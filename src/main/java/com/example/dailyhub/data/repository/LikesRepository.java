package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Likes;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
  boolean existsByUserAndPost(User user, Post post);

  Optional<Likes> findByPostAndUser(Post post, User user);

  Page<Likes> findAllByUser(User user, Pageable pageable);

}
