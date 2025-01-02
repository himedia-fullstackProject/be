package com.example.dailyhub.service;

import org.springframework.security.access.AccessDeniedException; // 추가
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.dto.UserDTO;
import com.example.dailyhub.data.entity.MainCategory;
import com.example.dailyhub.data.entity.SubCategory;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User; // User import 추가
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

import java.time.LocalDateTime; // 현재 시간 사용
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

        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인된 사용자 이름

        // 사용자 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        return Post.builder()
                .title(postDTO.getTitle())
                .image(postDTO.getImage())
                .description(postDTO.getDescription())
                .tag1(postDTO.getTag1())
                .tag2(postDTO.getTag2())
                .tag3(postDTO.getTag3())
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .user(user) // 현재 사용자 설정
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
                .userId(post.getUser() != null ? post.getUser().getId() : null) // 사용자 ID 추가
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userNickname(post.getUser() != null ? post.getUser().getNickname() : null) // 사용자 닉네임 추가
                .build();
    }

    // ID로 포스트 조회
    public Optional<PostDTO> getPostById(Long postId) {
        return postRepository.findById(postId).map(this::convertToDTO);
    }

    // 포스트 생성
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        post.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정
        post.setUpdatedAt(LocalDateTime.now()); // 수정 시간도 현재 시간으로 설정

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    // 포스트 업데이트
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        // 수정할 포스트 가져오기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        // 포스트 작성자 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인된 사용자 이름
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        if (!post.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to edit this post");
        }

        // 포스트 업데이트
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImage(postDTO.getImage());
        post.setTag1(postDTO.getTag1());
        post.setTag2(postDTO.getTag2());
        post.setTag3(postDTO.getTag3());
        post.setUpdatedAt(LocalDateTime.now()); // 수정 시간 업데이트

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    // 포스트 삭제
    public void deletePost(Long postId) {
        // 삭제할 포스트 가져오기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

//        // 현재 로그인된 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName(); // 로그인된 사용자 이름
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
//
//        // 포스트 작성자 확인 또는 admin 권한 확인
//        if (!post.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN")) {
//            throw new AccessDeniedException("You do not have permission to delete this post");
//        }

        postRepository.delete(post);
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
