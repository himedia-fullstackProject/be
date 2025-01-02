package com.example.dailyhub.controller;

import com.example.dailyhub.data.dto.SearchRequestDTO;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostDTO createdPostDTO = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPostDTO = postService.updatePost(id, postDTO);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestBody SearchRequestDTO searchRequest) {
        List<PostDTO> postDTOs = postService.searchPosts(
                searchRequest.getSearchTerm(),
                searchRequest.getMainCategoryId(),
                searchRequest.getSubCategoryId(),
                searchRequest.getSearchType()
        );
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postDTOs = postService.getAllPosts(pageable);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }
}
