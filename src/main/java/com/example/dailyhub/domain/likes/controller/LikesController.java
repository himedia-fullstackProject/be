package com.example.dailyhub.domain.likes.controller;


import com.example.dailyhub.domain.likes.dto.LikesDTO;
import com.example.dailyhub.domain.likes.service.LikesService;
import com.example.dailyhub.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Slf4j
public class LikesController {

    private final LikesService likesService;

    /**
     * 유저 별 좋아요 누른 포스트 조회
     * @param username
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return 포스트 정보, 페이지 네이션
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{username}")
    public ResponseEntity<PageResponse> getUserLikedPosts(
            @RequestParam(required = false) String username,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {

        return ResponseEntity.ok(likesService.getUserLikedListsPaged(username, pageNo, pageSize, sortBy));
    }

    @PostMapping
    public ResponseEntity<Boolean> toggleLike(@RequestBody LikesDTO likesDTO) {
        boolean isLiked = likesService.toggleLike(likesDTO.getUser_id(), likesDTO.getPost_id());
        return ResponseEntity.ok(isLiked);
    }

}


