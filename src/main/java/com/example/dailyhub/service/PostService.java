package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public PageResponse<PostDTO> searchPosts(String searchTerm, Pageable pageable) {
        Page<Post> searchResultPosts = postRepository.searchPosts(searchTerm, pageable);
        Page<PostDTO> postDTOPage = searchResultPosts.map(this::toPostDTO);

        return new PageResponse<>(postDTOPage);
    }

    public PageResponse<Post> getAllPosts(Pageable pageable) {
        Page<Post> AllPost = postRepository.findAll(pageable);
        return new PageResponse<>(AllPost);
    }

    public PageResponse<PostDTO> getAllHashTagSearchPosts(String tag, Pageable pageable) {
        Page<Post> tagSearchResultPost = postRepository.findPostsByHashtags(tag, pageable);
       Page<PostDTO> postDTOPage = tagSearchResultPost.map(this::toPostDTO);
       return new PageResponse<>(postDTOPage);
    } // 헤쉬태그 검색 페이지 네이션



    private PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .image(post.getImage())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                .mainCategoryId(post.getMainCategory() != null ? post.getMainCategory().getId() : null)
                .subCategoryId(post.getSubCategory() != null ? post.getSubCategory().getId() : null)
                .userId(post.getUser().getId() != null ? post.getUser().getId() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
