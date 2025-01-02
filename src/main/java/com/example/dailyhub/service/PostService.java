package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.dto.UserDTO;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.repository.MainCategoryRepository;
import com.example.dailyhub.data.repository.PostRepository;
import com.example.dailyhub.data.repository.SubCategoryRepository;
import com.example.dailyhub.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    // PostDTO를 Post로 변환
    private Post convertToEntity(PostDTO postDTO) {
        MainCategory mainCategory = mainCategoryRepository.findById(postDTO.getMainCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("MainCategory not found with id: " + postDTO.getMainCategoryId()));

        SubCategory subCategory = subCategoryRepository.findById(postDTO.getSubCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("SubCategory not found with id: " + postDTO.getSubCategoryId()));

        return Post.builder()
                .title(postDTO.getTitle())
                .image(postDTO.getImage())
                .description(postDTO.getDescription())
                .tag1(postDTO.getTag1())
                .tag2(postDTO.getTag2())
                .tag3(postDTO.getTag3())
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .build();
    }

    // Post를 PostDTO로 변환
    private PostDTO convertToDTO(Post post) {
        UserDTO userDTO = post.getUser() != null ? UserDTO.builder()
                .id(post.getUser().getId())
                .username(post.getUser().getUsername())
                .build() : null;

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .image(post.getImage())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                .mainCategoryId(post.getMainCategory().getId())
                .subCategoryId(post.getSubCategory().getId())
                .user(userDTO)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // ID로 포스트 조회
    public Optional<PostDTO> getPostById(Long postId) {
        return postRepository.findById(postId).map(this::convertToDTO);
    }

    // 포스트 생성
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    // 포스트 업데이트
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        post.setId(postId); // ID 설정
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    // 포스트 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 포스트 검색
    public List<PostDTO> searchPosts(String searchTerm, Long mainCategoryId, Long subCategoryId, String searchType) {
        List<Post> posts;

        // 카테고리 조건을 기반으로 포스트 검색
        if (searchType.equals("title") && (searchTerm == null || searchTerm.isEmpty())) {
            posts = postRepository.findByMainCategoryIdAndSubCategoryId(mainCategoryId, subCategoryId);
        } else {
            posts = postRepository.searchPosts(searchTerm, mainCategoryId, subCategoryId, searchType);
        }

        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    // 모든 포스트 조회
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDTO);
    }

    // 카테고리 조회
    public Optional<MainCategory> findMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    public Optional<SubCategory> findSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }
}
