package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository <Likes, Long> {
}
