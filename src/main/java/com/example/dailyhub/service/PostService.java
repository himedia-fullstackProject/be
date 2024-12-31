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

    public List<Post> searchPosts(String searchTerm) {
        return postRepository.searchPosts(searchTerm);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
