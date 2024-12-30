package com.example.dailyhub.service;

import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getPostById(Long postId) {        //포스트조회
        return postRepository.findById(postId);
    }

    public Post createPost(Post post) {                     //포스트생성
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post updatedPost) { //포스트수정
        updatedPost.setId(postId);
        return postRepository.save(updatedPost);
    }

    public void deletePost(Long postId) {                   //포스트삭제
        postRepository.deleteById(postId);
    }

    public List<Post> searchPosts(String searchTerm) {      //포스트검색
        return postRepository.searchPosts(searchTerm);
    }
}
