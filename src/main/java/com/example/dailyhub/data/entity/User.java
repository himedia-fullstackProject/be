package com.example.dailyhub.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString(exclude = {"posts","likes"})
@EqualsAndHashCode(exclude = {"posts","likes"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tbl")
public class User {

  @Id
  @NotEmpty(message = "아이디를 입력해주세요.")
  private String username;

  @NotEmpty(message = "비밀번호는 비어있을 수 없습니다.")
  @Size(max = 255)
  private String password;

  @Size(min = 1, max = 10)
  private String nickname;

  @Size(max = 11)
  private String phoneNumber;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Post> posts = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Likes> likes = new ArrayList<>();
}
