package com.example.dailyhub.controller;


import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.repository.LikesRepository;
import com.example.dailyhub.data.repository.PostRepository;
import com.example.dailyhub.data.repository.UserRepository;
import com.example.dailyhub.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/likes")
@Slf4j


public class LikesController {
    private final LikesService likesService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    //유저 별 좋아요 누른 포스트 조회
    @GetMapping("/{id}")
    public List<PostDTO> getLikesList(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패 "));
        return likesService.readLikesPostsByUser(user);
    }


    @PostMapping
    public void changeLikes(@RequestBody PostDTO postDTO) {
        String userName = postDTO.getUsername();
        Long postId = postDTO.getId();
        String user = UserRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("유저와 포스트 조회 실패 "));

        likesService.changeLikes(user, post);
    }
}


}