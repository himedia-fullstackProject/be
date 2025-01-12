package com.example.dailyhub.domain.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikesDTO {
    private Long id; // 좋아요 ID
    private Long user_id; // 좋아요를 누른 사용자 정보
    private Long post_id;
}
