package com.example.dailyhub.domain.likes.repository.querydsl;

import com.example.dailyhub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesRepositoryCustom {

  Page<Post> findPostByUserId(Long userId, Pageable pageable);

}
