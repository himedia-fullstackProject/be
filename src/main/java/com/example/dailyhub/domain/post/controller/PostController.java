package com.example.dailyhub.domain.post.controller;

import com.example.dailyhub.domain.post.dto.PostDTO;
import com.example.dailyhub.domain.post.service.PostService;
import com.example.dailyhub.dto.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  /**
   * 글 자세히 보기
   * @param id
   * @return id 값에 해당하는 post 정보
   */
  @PreAuthorize("permitAll()")
  @GetMapping("/{id}")
  public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPost(id));
  }

  /**
   * 글 작성
   * @param postDTO
   * @return 포스트 저장 상태
   */
  @PreAuthorize("hasRole('ROLE_USER')")
  @PostMapping
  public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
    PostDTO createdPostDTO = postService.createPost(postDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDTO);
  }

  /**
   * 글 수정
   * @param id
   * @param postDTO
   * @return 포스트 수정 상태
   */
  @PreAuthorize("hasRole('ROLE_USER')")
  @PutMapping("/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
    PostDTO updatedPostDTO = postService.updatePost(id, postDTO);
    return ResponseEntity.ok(updatedPostDTO);
  }

  /**
   * 글 삭제
   * @param id
   * @return 포스트 삭제 상태
   */
  @PreAuthorize("hasRole('ROLE_USER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * 글 검색
   * @param searchTerms
   * @param pageNo
   * @param pageSize
   * @param sortBy
   * @return 검색 결과 페이지 네이션 출력
   */
  @PreAuthorize("permitAll()")
  @GetMapping("/search")
  public PageResponse searchPosts(
      @RequestParam(required = false) String searchTerms,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "6", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
  ) {
    return postService.searchPosts(searchTerms, pageNo, pageSize, sortBy);
  }


    /**
     * 태그 검색
     * @param tag
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return 태그 눌렀을 때, 페이지네이션 결과값 출력
     */
  @PreAuthorize("permitAll()")
  @GetMapping("/search/tag")
  public PageResponse searchPostsByTag(
      @RequestParam(required = false) String tag,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "6", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
  ) {
    return postService.searchPostsByTag(tag, pageNo, pageSize, sortBy);
  }
  // ToDo 현재 tag 검색 잘 안되는거 같음 확인해야됨
    /**
     * 유저가 작성한 포스트 가져오기(페이지네이션)
     * @param username
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return 유저 작성글 페이지네이션
     */
  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping
  public PageResponse getAllPostsByUser(
      @RequestParam(required = false) String username,
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "6", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
  ) {
    return postService.getAllPostsByUser(username, pageNo, pageSize, sortBy);
  }

    /**
     * 모든 포스트 가져오기(페이지네이션)
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return 모든 포스트 결과 페이지네이션 값
     */
  @PreAuthorize("permitAll()")
  @GetMapping("/all/pagination")
  public ResponseEntity<PageResponse> getAllPostsByPagination(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "6", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
  ) {
    return ResponseEntity.ok(postService.getAllPostsByPagination(pageNo,pageSize,sortBy));
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/all/list")
  public ResponseEntity<List<PostDTO>> getAllPostByList() {
    return ResponseEntity.ok(postService.getAllPostsbyList());
  }


}