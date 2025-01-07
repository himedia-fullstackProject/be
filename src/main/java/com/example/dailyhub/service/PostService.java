package com.example.dailyhub.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
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

import com.example.dailyhub.data.dto.PageResponse;

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

    private PostDTO convertToDTO(Post post, boolean includeUser) {
        PostDTO.PostDTOBuilder builder = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .image(post.getImage())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                .mainCategoryId(post.getMainCategory() != null ? post.getMainCategory().getId() : null)
                .subCategoryId(post.getSubCategory() != null ? post.getSubCategory().getId() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt());

        if (includeUser) {
            String username = post.getUser() != null ?
                    userRepository.findByUserId(post.getUser().getId())
                            .orElse("유저 정보 없음") : "유저 정보 없음";
            builder.userId(post.getUser() != null ? post.getUser().getId() : null)
                    .username(username);
        } else {
            builder.userId(null)
                    .username("정보 없음");
        }

        return builder.build();
    }

    @Transactional
    public Optional<PostDTO> getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        return Optional.of(convertToDTO(post, true)); // 유저 정보를 포함하여 반환
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost, true); // 유저 정보를 포함하여 반환
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        validateUserAuthorization(post); // 사용자 권한 확인
        updatePostDetails(post, postDTO); // 포스트 세부정보 업데이트

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost, true); // 유저 정보를 포함하여 반환
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        validateUserAuthorization(post); // 사용자 권한 확인
        postRepository.delete(post);
    }

    public PageResponse<PostDTO> searchCategoryAndPosts(String searchTerm,Pageable pageable) {
        Page<Post> posts = postRepository.searchPosts(searchTerm, pageable);
        Page<PostDTO> postDTOs = posts.map(post -> convertToDTO(post, true)); // 유저 정보 포함
        return new PageResponse<>(postDTOs);
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

    public PageResponse<PostDTO> getAllPosts(String username, Pageable pageable) {
        Long userId = userRepository.findUserIdByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        Page<Post> allPosts = postRepository.getAllPostByUserId(userId, pageable);
        Page<PostDTO> postDTOs = allPosts.map(post -> convertToDTO(post, true)); // 유저 정보 포함
        return new PageResponse<>(postDTOs);
    }


    public PageResponse<PostDTO> getAllHashTagSearchPosts(String tag, Pageable pageable) {
        Page<Post> tagSearchResultPost = postRepository.findPostsByHashtags(tag, pageable);
        Page<PostDTO> postDTOPage = tagSearchResultPost.map(post -> convertToDTO(post, false)); // 유저 정보 비포함
        return new PageResponse<>(postDTOPage);
    }

    public PageResponse<PostDTO> getAllPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable); // Page<Post>를 가져옴
        return new PageResponse<>(posts.map(post -> convertToDTO(post, true))); // Post를 PostDTO로 변환 후 PageResponse에 전달
    }

    public List<PostDTO> getAllPost2(boolean includeUser) {
        List<Post> posts = postRepository.findAll(); // 모든 Post 데이터를 가져옴
        return posts.stream()
                .map(post -> convertToDTO(post, includeUser)) // convertToDTO 메서드 호출
                .collect(Collectors.toList()); // List<PostDTO>로 변환
    }



}
