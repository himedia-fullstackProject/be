package com.example.dailyhub.domain.post.repository.querydsl;

import com.example.dailyhub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

  Page<Post> searchPostsByTitle(String searchTerm, Pageable pageable);

  Page<Post> searchPostsByTagName(String tagName, Pageable pageable);

  Page<Post> findPostsByUserName(String userName, Pageable pageable);

}
