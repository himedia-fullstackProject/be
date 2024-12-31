package com.example.dailyhub.data.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
  private Long id;
  private String title;
  private String description;
  private String tag1;
  private String tag2;
  private String tag3;
  private String image;
  private Long mainCategoryId;
  private Long subCategoryId;
  private Long userId;

}
