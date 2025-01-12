package com.example.dailyhub.security.jwt;

import com.example.dailyhub.domain.user.entity.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private final User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

  public Long getId() {
    return user.getId();
  }

  public String getNickname() {
    return user.getNickname();
  }

  public String getPhoneNumber() {
    return user.getPhoneNumber();
  }

  public LocalDate getJoinDate() { return user.getJoinDate();}

  public String getRole() {
    return user.getRole().name();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRole() == User.UserRole.ADMIN
        ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        : List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
