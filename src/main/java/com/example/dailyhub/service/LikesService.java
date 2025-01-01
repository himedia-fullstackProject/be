package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikesService {
    public Page<PostDTO> readLikesPostsByUser(User user , Pageable pageable);

    public void changeLikes(User user, Post post);

//    public Page<PostDTO> readLikesPostsByUser(User user, Pageable pageable);
}