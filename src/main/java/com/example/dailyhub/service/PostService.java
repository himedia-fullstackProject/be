package com.example.dailyhub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.repository.MainCategoryRepository;
import com.example.dailyhub.data.repository.PostRepository;
import com.example.dailyhub.data.repository.SubCategoryRepository;
import com.example.dailyhub.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private Post convertToEntity(PostDTO postDTO) {
        MainCategory mainCategory = mainCategoryRepository.findById(postDTO.getMainCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("MainCategory not found with id: " + postDTO.getMainCategoryId()));
        SubCategory subCategory = subCategoryRepository.findById(postDTO.getSubCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("SubCategory not found with id: " + postDTO.getSubCategoryId()));
        User user = getCurrentUser(); // 현재 로그인된 사용자 정보 가져오기

        return Post.builder()
                .title(postDTO.getTitle())
                .image(postDTO.getImage())
                .description(postDTO.getDescription())
                .tag1(postDTO.getTag1())
                .tag2(postDTO.getTag2())
                .tag3(postDTO.getTag3())
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private PostDTO convertToDTO(Post post) {
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
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                .build();
    }

    @Transactional
    public Optional<PostDTO> getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        return Optional.of(convertToDTO(post));
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        validateUserAuthorization(post); // 사용자 권한 확인
        updatePostDetails(post, postDTO); // 포스트 세부정보 업데이트

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        validateUserAuthorization(post); // 사용자 권한 확인
        postRepository.delete(post);
    }

    public List<PostDTO> searchPosts(String searchTerm, Long mainCategoryId, Long subCategoryId, String searchType) {
        List<Post> posts = postRepository.searchPosts(searchTerm, mainCategoryId, subCategoryId, searchType);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDTO);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인된 사용자 이름
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    private void validateUserAuthorization(Post post) {
        User user = getCurrentUser();
        if (!post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to perform this operation.");
        }
    }

    private void updatePostDetails(Post post, PostDTO postDTO) {
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImage(postDTO.getImage());
        post.setTag1(postDTO.getTag1());
        post.setTag2(postDTO.getTag2());
        post.setTag3(postDTO.getTag3());
        post.setUpdatedAt(LocalDateTime.now()); // 수정 시간 업데이트
    }
}
