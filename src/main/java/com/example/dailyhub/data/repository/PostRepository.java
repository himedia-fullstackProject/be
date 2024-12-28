package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
