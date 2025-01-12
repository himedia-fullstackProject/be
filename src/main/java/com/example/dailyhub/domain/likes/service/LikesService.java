package com.example.dailyhub.domain.likes.service;
import com.example.dailyhub.domain.post.dto.PostDTO;
import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.dto.PageResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public interface LikesService {

    PageResponse getUserLikedListsPaged(String username, int pageNo, int pageSize, String sortBy);

    boolean toggleLike(Long userId, Long postId);

    default PostDTO toDTO(Post post) {
        return PostDTO.builder()
            .id(post.getId())
            .title(post.getTitle())
            .description(post.getDescription())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .mainCategoryId(post.getMainCategory().getId())
            .subCategoryId(post.getSubCategory().getId())
            .build();
    }

    default PageResponse createPageResponse(Page<Post> LikesPage, List<PostDTO> content, int pageNo,
        int pageSize) {
        return PageResponse.builder()
            .content(content)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(LikesPage.getTotalElements())
            .totalPages(LikesPage.getTotalPages())
            .last(LikesPage.isLast())
            .build();
    }



//    PageResponse<PostDTO> readLikesPostsByUser(Long userId , Pageable pageable);

//    boolean changeLikes(User user, Post post);

//    default PostDTO convertPostToDTO(Post post) {
//        String username = userRepository.findByUserId(post.getUser().getId())
//            .orElseThrow(() -> new RuntimeException("유저 닉네임 조회 실패"));
//        return PostDTO.builder()
//            .id(post.getId())
//            .title(post.getTitle())
//            .image(post.getImage())
//            .description(post.getDescription())
//            .tag1(post.getTag1())
//            .tag2(post.getTag2())
//            .tag3(post.getTag3())
//            .mainCategoryId(post.getMainCategory().getId())
//            .subCategoryId(post.getSubCategory().getId())
//            .userId(post.getUser().getId()) // 작성자 정보 추가
//            .username(username)
//            .createdAt(post.getCreatedAt())
//            .updatedAt(post.getUpdatedAt())
//            .build();
//    }

}