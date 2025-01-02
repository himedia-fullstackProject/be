package com.example.dailyhub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private String searchTerm; // 검색어
    private Long mainCategoryId; // 메인 카테고리 ID
    private Long subCategoryId; // 서브 카테고리 ID (선택 사항)
    private String searchType; // 검색 유형 (제목, 내용, 글쓴이 등)
}