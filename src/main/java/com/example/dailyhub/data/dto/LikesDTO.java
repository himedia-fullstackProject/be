package com.example.dailyhub.data.dto;

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
    private Long count; // 좋아요 수
    private Long user; // 좋아요를 누른 사용자 정보
    private Long post;
}
