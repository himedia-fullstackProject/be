package com.example.dailyhub.domain.likes.repository;

import com.example.dailyhub.domain.likes.entity.Likes;
import com.example.dailyhub.domain.likes.repository.querydsl.LikesRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

  @Query("select li from Likes li where li.post.id = :postId and li.user.id = :userId")
  Optional<Likes> findByPostAndUser(Long postId, Long userId);
}
