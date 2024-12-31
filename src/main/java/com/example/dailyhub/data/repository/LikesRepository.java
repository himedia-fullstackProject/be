package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Likes;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface LikesRepository extends JpaRepository <Likes, Long> {
    Collection<Object> findAllLikesByUser(User user);

    boolean existByUserAndPost(User user, Post post);

    Optional<Object> findLikesByPostAndUser(Post post, User user);
}
