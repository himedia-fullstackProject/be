package com.example.dailyhub.data.dto;

import com.example.dailyhub.data.entity.User.UserRole;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private Long id;
  private String username;
  private String password;
  private String nickname;
  private String phoneNumber;
  private LocalDate birthday;
  private UserRole role;

  public enum UserRole {
    ADMIN,
    USER
  }

}
