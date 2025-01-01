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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/likes")
@Slf4j


public class LikesController {
    private final LikesService likesService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    //유저 별 좋아요 누른 포스트 조회 / 페이지 네이션
    @GetMapping("/{id}")
    public ResponseEntity<Page<PostDTO>> getUserLikedPosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));

        // 페이지 당 포스트 갯수  6, 내림차순 정렬
        Pageable pageable = PageRequest.of(page, 6, Sort.by("createdAt").descending());
        Page<PostDTO> likedPosts = likesService.readLikesPostsByUser(user, pageable);

        return ResponseEntity.ok(likedPosts);
    }


    @PostMapping //좋아요 추가&취소
    public void changeLikes(@RequestBody PostDTO postDTO) {
        String username = postDTO.getUsername();
        Long postId = postDTO.getId();
// postDTO 에서 유저 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));
//
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트 조회 실패"));

        likesService.changeLikes(user, post);
    }
}


