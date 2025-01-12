package com.example.dailyhub.domain.post.entity;

import com.example.dailyhub.domain.category.entity.MainCategory;
import com.example.dailyhub.domain.category.entity.SubCategory;
import com.example.dailyhub.domain.image.entity.Image;
import com.example.dailyhub.domain.likes.entity.Likes;
import com.example.dailyhub.domain.tag.entity.PostTag;
import com.example.dailyhub.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@ToString(exclude = {"likes", "mainCategory", "subCategory", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_tbl")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true)
  @Size(min = 1)
  private String title;

  @Column(nullable = true)
  @Size(min = 5)
  private String description;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
  @Builder.Default
  private List<Likes> likes = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY,mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<PostTag> postTags = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id")
  private MainCategory mainCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sub_category_id")
  private SubCategory subCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private Image image;
}
