package com.example.dailyhub.domain.post.service;

import com.example.dailyhub.domain.category.entity.MainCategory;
import com.example.dailyhub.domain.category.entity.SubCategory;
import com.example.dailyhub.domain.image.dto.ImageDTO;
import com.example.dailyhub.domain.post.dto.PostDTO;
import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.domain.user.entity.User;
import com.example.dailyhub.dto.PageResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PostService {

  PostDTO getPost(Long id);

  PostDTO createPost(PostDTO postDTO);

  PostDTO updatePost(Long id, PostDTO postDTO);

  void deletePost(Long id);

  PageResponse searchPosts(String searchTerms, int pageNo, int pageSize, String sortBy);

  PageResponse searchPostsByTag(String tag, int pageNo, int pageSize, String sortBy);

  PageResponse getAllPostsByUser(String username, int pageNo, int pageSize, String sortBy);

  PageResponse getAllPostsByPagination(int pageNo, int pageSize, String sortBy);

  List<PostDTO> getAllPostsbyList();

  default PostDTO toDTO(Post post) {
    return PostDTO.builder()
        .id(post.getId())
        .title(post.getTitle())
        .description(post.getDescription())
        .mainCategoryId(post.getMainCategory().getId())
        .subCategoryId(post.getSubCategory().getId())
        .userId(post.getUser().getId())
        .userNickname(post.getUser().getNickname())
        .createdAt(post.getCreatedAt())
        .updatedAt(post.getUpdatedAt())
        .image(post.getImage() != null
          ? ImageDTO.builder()
            .id(post.getImage().getId())
            .url(post.getImage().getUrl())
            .build()
          : null)
        .tags(post.getPostTags().stream()
            .map(postTag -> postTag.getTag().getTagName())
            .toList()
        )
        .build();
  }

  default Post toEntity(PostDTO postDTO) {
    return Post.builder()
        .id(postDTO.getId())
        .title(postDTO.getTitle())
        .description(postDTO.getDescription())
        .mainCategory(postDTO.getMainCategoryId() != null
            ? MainCategory.builder().id(postDTO.getMainCategoryId()).build()
            : null)
        .subCategory(postDTO.getSubCategoryId() != null
            ? SubCategory.builder().id(postDTO.getSubCategoryId()).build()
            : null)
        .user(postDTO.getUserId() != null
        ? User.builder().id(postDTO.getUserId()).build()
            : null)
        .build();
  }

  default PageResponse createPageResponse(Page<Post> PostPage, List<PostDTO> content, int pageNo,
      int pageSize) {
    return PageResponse.builder()
        .content(content)
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(PostPage.getTotalElements())
        .totalPages(PostPage.getTotalPages())
        .last(PostPage.isLast())
        .build();
  }

}
