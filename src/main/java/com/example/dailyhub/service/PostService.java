package com.example.dailyhub.service;

import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post updatedPost) {
        updatedPost.setId(postId);
        return postRepository.save(updatedPost);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> searchPosts(String searchTerm , Pageable pageable) {
        return postRepository.searchPosts(searchTerm , pageable);
    } // 검색결과 페이지네이션 수정

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> getAllHashTagSearchPosts(String tag , Pageable pageable) {
        return postRepository.findPostsByHashtags(tag , pageable);
    } // 헤쉬태그 검색 페이지 네이션

}
