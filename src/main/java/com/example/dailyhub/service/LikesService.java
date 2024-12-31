package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;

import java.util.List;

public interface LikesService {
   public List<PostDTO> readLikesPostsByUser(User user);

    void changeLikes(User user, Post post);
}
