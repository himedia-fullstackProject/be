package com.example.dailyhub.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor

public class PageResponse<T>{
    private List<T>content;
    private int totalPosts; // 전채  포스트 수
    private int totalPages; // 전체 페이지 갯수
    private int nowPage; //현재 페이지
    private int size; //한페이지에 있는 포스트 갯수
    private boolean firstPage;
    private boolean lastPage;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPosts = (int)page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.nowPage = page.getNumber();
        this.size = page.getSize();
        this.firstPage = page.isFirst();
        this.lastPage = page.isLast();
        this.totalPosts = page.getNumberOfElements();

    }


}
