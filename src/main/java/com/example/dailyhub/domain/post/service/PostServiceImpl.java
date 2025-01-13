package com.example.dailyhub.domain.post.service;

import com.example.dailyhub.domain.image.entity.Image;
import com.example.dailyhub.domain.image.repository.ImageRepository;
import com.example.dailyhub.domain.post.dto.PostDTO;
import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.domain.post.repository.PostRepository;
import com.example.dailyhub.domain.tag.entity.PostTag;
import com.example.dailyhub.domain.tag.entity.Tag;
import com.example.dailyhub.domain.tag.repository.PostTagRepository;
import com.example.dailyhub.domain.tag.repository.TagRepository;
import com.example.dailyhub.domain.tag.util.TagUtil;
import com.example.dailyhub.domain.user.entity.User;
import com.example.dailyhub.domain.user.repository.UserRepository;
import com.example.dailyhub.dto.PageResponse;
import com.example.dailyhub.util.AwsS3Util;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final ImageRepository imageRepository;
  private final AwsS3Util awsS3Util;
  private final TagUtil tagUtil;
  private final PostTagRepository postTagRepository;
  private final TagRepository tagRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  @Override
  public PostDTO getPost(Long id) {
    log.info("getPost start...");

    Post post = postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Post not found"));
    return toDTO(post);
  }

  @Override
  public PostDTO createPost(PostDTO postDTO) {
    log.info("createPost start...");

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("username is...", username);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Post post = toEntity(postDTO);
    post.setUser(user);
    post.setCreatedAt(LocalDateTime.now());

    Post savedPost = postRepository.save(post);
    log.info("Image processed and linked to post ID: {}", savedPost.getId());

    log.info("Mapping tags to post...");
    List<PostTag> postTags = tagUtil.mapTagsToPost(post, postDTO.getTags());
    post.setPostTags(postTags);
    log.info("Tags mapped successfully: {}", postTags.size());

    if (postDTO.getImage() != null) {
      log.info("Processing image with ID: {}", postDTO.getImage().getId());
      Image image = imageRepository.findById(postDTO.getImage().getId())
          .orElseThrow(() -> new RuntimeException("Image not found"));

      if (!image.isTemporary()) {
        throw new IllegalStateException("Image is already finalized");
      }

      image.setPost(post);
      image.setTemporary(false);
      imageRepository.save(image);
      post.setImage(image);

    }

    log.info("createPost end...");
    return toDTO(savedPost);
  }

  // ToDo 현재 글 작성시, 2개 작성되고, createAt 값 안들어감

  @Transactional
  @Override
  public PostDTO updatePost(Long id, PostDTO postDTO) {
    log.info("updatePost start...");

    Post post = postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Post not found"));

    post.setTitle(postDTO.getTitle());
    post.setDescription(postDTO.getDescription());
    post.setUpdatedAt(LocalDateTime.now());

    List<PostTag> postTags = tagUtil.mapTagsToPost(post, postDTO.getTags());
    post.setPostTags(postTags);

    if (postDTO.getImage() != null) {
      Image image = imageRepository.findById(postDTO.getImage().getId())
          .orElseThrow(() -> new RuntimeException("Image not found"));
      if (!image.isTemporary()) {
        throw new IllegalStateException("Image is already finalized");
      }
      image.setPost(post);
      image.setTemporary(false);
      imageRepository.save(image);
      post.setImage(image);
    }

    Post updatedPost = postRepository.save(post);
    return toDTO(updatedPost);
  }

  @Transactional
  @Override
  public void deletePost(Long id) {
    log.info("deletePost start...");

    Post post = postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Post not found"));

    if (post.getImage() != null) {
      String s3Key = post.getImage().getUrl().substring(post.getImage().getUrl().lastIndexOf("/"));
      awsS3Util.deleteFilesByKeys(List.of(s3Key));
    }

    if (post.getImage() != null) {
      imageRepository.delete(post.getImage());
    }

    List<PostTag> postTags = post.getPostTags();
    for (PostTag postTag : postTags) {
      Tag tag = postTag.getTag();
      postTagRepository.delete(postTag);

      tag.setCount(tag.getCount() - 1);
      if (tag.getCount() <= 0) {
        tagRepository.delete(tag);
      } else {
        tagRepository.save(tag);
      }
    }

    postRepository.delete(post);
  }

  // ToDo 현재 글 삭제 할때, tag count 값 줄어들지 않음

  @Transactional(readOnly = true)
  @Override
  public PageResponse searchPosts(String searchTerms, int pageNo, int pageSize,
      String sortBy) {
    log.info("searchPosts start...");

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Post> postPage = postRepository.searchPostsByTitle(searchTerms, pageable);

    List<Post> postList = postPage.getContent();

    List<PostDTO> content = postList.stream().map(this::toDTO).toList();

    return createPageResponse(postPage, content, pageNo, pageSize);
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse searchPostsByTag(String tag, int pageNo, int pageSize,
      String sortBy) {
    log.info("searchPostsByTag start...");

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Post> postPage = postRepository.searchPostsByTagName(tag, pageable);

    List<Post> postList = postPage.getContent();

    List<PostDTO> content = postList.stream().map(this::toDTO).toList();

    return createPageResponse(postPage, content, pageNo, pageSize);
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse getAllPostsByUser(String username, int pageNo, int pageSize,
      String sortBy) {
    log.info("getAllPostsByUser start...");

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Post> postPage = postRepository.findPostsByUserName(username, pageable);

    List<Post> postList = postPage.getContent();

    List<PostDTO> content = postList.stream().map(this::toDTO).toList();

    return createPageResponse(postPage, content, pageNo, pageSize);
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse getAllPostsByPagination(int pageNo, int pageSize, String sortBy) {
    log.info("getAllPostsByPagination start...");

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Post> postPage = postRepository.findAll(pageable);

    List<Post> postList = postPage.getContent();

    List<PostDTO> content = postList.stream().map(this::toDTO).toList();

    return createPageResponse(postPage, content, pageNo, pageSize);
  }

  @Transactional(readOnly = true)
  @Override
  public List<PostDTO> getAllPostsbyList() {
    log.info("getAllPostsByList start...");

    List<Post> post = postRepository.findAll();
    return post.stream().map(this::toDTO).toList();
  }

}