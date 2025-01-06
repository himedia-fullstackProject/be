package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesService {
    public PageResponse<PostDTO> readLikesPostsByUser(Long userId , Pageable pageable);

    public boolean changeLikes(User user, Post post);


}