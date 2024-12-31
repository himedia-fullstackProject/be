package com.example.dailyhub.service;

import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getPostById(Long postId) {        // 포스트 조회
        return postRepository.findById(postId);
    }

    public Post createPost(Post post) {                     // 포스트 생성
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post updatedPost) { // 포스트 수정
        updatedPost.setId(postId);
        return postRepository.save(updatedPost);
    }

    public void deletePost(Long postId) {                   // 포스트 삭제
        postRepository.deleteById(postId);
    }

    public List<Post> searchPosts(String searchTerm) {      // 포스트 검색
        return postRepository.searchPosts(searchTerm);
    }

    public Page<Post> getAllPosts(Pageable pageable) {      // 모든 포스트 조회 및 페이지네이션
        return postRepository.findAll(pageable);
    }
}
