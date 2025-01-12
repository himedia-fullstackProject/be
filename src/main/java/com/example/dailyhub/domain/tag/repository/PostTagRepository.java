package com.example.dailyhub.domain.tag.repository;

import com.example.dailyhub.domain.tag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

}
