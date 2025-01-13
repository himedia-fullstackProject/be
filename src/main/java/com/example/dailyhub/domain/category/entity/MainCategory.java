package com.example.dailyhub.domain.category.entity;

import com.example.dailyhub.domain.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = {"subCategories", "posts"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "main_category_tbl")
public class MainCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, max = 50)
  @NotEmpty(message = "카테고리 이름은 비어있을 수 없습니다.")
  private String categoryName;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainCategory", cascade = CascadeType.ALL)
  @Builder.Default
  private List<SubCategory> subCategories = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainCategory", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Post> posts = new ArrayList<>();
}