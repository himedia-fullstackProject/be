package com.example.dailyhub.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  @Size(min = 5, max = 200)
  private String description;

  @Column(nullable = true)
  @Size(max = 10)
  private String tag1;

  @Column(nullable = true)
  @Size(max = 10)
  private String tag2;

  @Column(nullable = true)
  @Size(max = 10)
  private String tag3;

  @Column(nullable = false)
  private String image;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Likes> likes = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id")
  private MainCategory mainCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sub_category_id")
  private SubCategory subCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

<<<<<<< HEAD

=======
>>>>>>> 31045e548a1cb1303acf574440ee022e81d04b54
}
