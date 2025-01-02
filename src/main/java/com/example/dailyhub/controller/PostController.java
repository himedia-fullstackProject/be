package com.example.dailyhub.controller;

import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        // likes는 초기화하지 않음
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(convertToDTO(createdPost), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        post.setId(id); // ID를 설정
        // likes는 수정하지 않음
        Post updatedPost = postService.updatePost(id, post);
        return new ResponseEntity<>(convertToDTO(updatedPost), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostDTO>> searchPosts(
            @RequestParam(required = true) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        if (StringUtils.isEmpty(searchTerm)) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.searchPosts(searchTerm, pageable);
        Page<PostDTO> postDTOs = posts.map(this::convertToDTO);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping("/search/tag")
    public ResponseEntity<Page<PostDTO>> searchPostsByTag(
            @RequestParam(required = true) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("태그를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> tagPosts = postService.getAllHashTagSearchPosts(tag, pageable);
        Page<PostDTO> tagPostDTOs = tagPosts.map(this::convertToDTO);
        return new ResponseEntity<>(tagPostDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getAllPosts(pageable);
        Page<PostDTO> postDTOs = posts.map(this::convertToDTO);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    // Entity to DTO 변환 메서드 (likes는 포함하지 않음)
    private PostDTO convertToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .image(post.getImage())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                // likes는 포함하지 않음
                .mainCategoryId(post.getMainCategory() != null ? post.getMainCategory().getId() : null)
                .subCategoryId(post.getSubCategory() != null ? post.getSubCategory().getId() : null)
                .userId(post.getUser().getId() != null ? post.getUser().getId() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // DTO to Entity 변환 메서드 (likes는 포함하지 않음)
    private Post convertToEntity(PostDTO postDTO) {
        return Post.builder()
                .title(postDTO.getTitle())
                .image(postDTO.getImage())
                .description(postDTO.getDescription())
                .tag1(postDTO.getTag1())
                .tag2(postDTO.getTag2())
                .tag3(postDTO.getTag3())
                // likes는 포함하지 않음
                .build();
    }


}
