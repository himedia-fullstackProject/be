package com.example.dailyhub.controller;

import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.entity.Post;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(postDTO -> new ResponseEntity<>(postDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        if (postDTO.getImage() == null || postDTO.getImage().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PostDTO createdPostDTO = postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPostDTO = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<PostDTO>> searchPosts(
            @RequestParam(required = true) String searchTerm,
            @RequestParam(required = false) Long mainCategoryId,
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) String searchType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        if (StringUtils.isEmpty(searchTerm)) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<PostDTO> posts = postService.searchCategoryAndPosts(searchTerm,mainCategoryId,subCategoryId,searchType, pageable);
        return ResponseEntity.ok(posts);
        //        Page<PostDTO> postDTOs = posts.map(this::convertToDTO);
//        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping("/search/tag")
    public HttpEntity<PageResponse<PostDTO>> searchPostsByTag(
            @RequestParam(required = true) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("태그를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<PostDTO> searchTagPosts = postService.getAllHashTagSearchPosts(tag, pageable);

        return ResponseEntity.ok(searchTagPosts);
    }


    @GetMapping
    public ResponseEntity<PageResponse<PostDTO>> getAllPosts(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 수정된 메소드 호출
        PageResponse<PostDTO> posts = postService.getAllPosts(username, pageable);
        return ResponseEntity.ok(posts);
    }



}
g