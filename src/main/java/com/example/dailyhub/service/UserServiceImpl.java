package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.UserDTO;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.entity.User.UserRole;
import com.example.dailyhub.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 암호화
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

  @Transactional(readOnly = true)
  @Override
  public UserDTO getUser(Long id) {
    User user = userRepository.findById(id).orElse(null);
    return user != null ? toDTO(user) : null;
  }

  @Transactional
  @Override
  public void addUser(UserDTO userDTO) {
    String username = userDTO.getUsername();
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new RuntimeException("이미 존재하는 사용자명입니다.");
    }

    User user = toEntity(userDTO);
    user.setJoinDate(LocalDate.now());
    user.setRole(UserRole.USER);
    user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  @Override
  public boolean existsByUsername(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
  }

  // 로그인 메서드 추가
  @Transactional(readOnly = true)
  public UserDTO login(String username, String password) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    return toDTO(user); // 로그인 성공 시 UserDTO 반환
  }
}
