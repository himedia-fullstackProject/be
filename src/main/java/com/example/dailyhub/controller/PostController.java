package com.example.dailyhub.controller;

import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.dto.SearchRequestDTO;
import com.example.dailyhub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * Post detail 보는 메서드
     *
     * @param id
     * @return id 값에 해당하는 post 정보
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(postDTO -> new ResponseEntity<>(postDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 글 작성
     *
     * @param postDTO
     * @return 포스트 저장 상태
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        if (postDTO.getImage() == null || postDTO.getImage().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PostDTO createdPostDTO = postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDTO);
    }

    /**
     * 글 수정
     *
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
     *
     * @param id
     * @return 포스트 삭제 상태
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public ResponseEntity<PageResponse<PostDTO>> searchPosts(
            @RequestBody SearchRequestDTO searchRequest) {
        if (StringUtils.isEmpty(searchRequest.getSearchTerm())) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());

        PageResponse<PostDTO> posts = postService.searchCategoryAndPosts(
                searchRequest.getSearchTerm(),
                searchRequest.getMainCategoryId(),
                searchRequest.getSubCategoryId(),
                searchRequest.getSearchType(),
                pageable
        );

        return ResponseEntity.ok(posts);
    }




    /**
     * 태그 검색
     *
     * @param tag
     * @param page
     * @param size
     * @return 태그 검색 결과
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/search/tag")
    public HttpEntity<PageResponse<PostDTO>> searchPostsByTag(
            @RequestParam(required = true) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("태그를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<PostDTO> searchTagPosts = postService.getAllHashTagSearchPosts(tag, pageable);

        return ResponseEntity.ok(searchTagPosts);
    }

    /**
     * 유저 별 작성 글 가져오기
     *
     * @param username
     * @param page
     * @param size
     * @return 유저 별 작성 글 페이지네이션 정보
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<PageResponse<PostDTO>> getAllPosts(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<PostDTO> posts = postService.getAllPosts(username, pageable);

        return ResponseEntity.ok(posts);
    }

    /**
     * 전체 포스트 조회
     * @param page
     * @param size
     * @return 전체 포스트 페이지네이션 정보
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<PageResponse<PostDTO>> getAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<PostDTO> posts = postService.getAllPost(pageable);
        return ResponseEntity.ok(posts);
    }

}