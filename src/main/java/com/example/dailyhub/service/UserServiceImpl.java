package com.example.dailyhub.service;

import com.example.dailyhub.data.dto.UserDTO;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.entity.User.UserRole;
import com.example.dailyhub.data.repository.UserRepository;
import com.example.dailyhub.security.jwt.CustomUserDetails;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

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
    Optional<User> existingUser = userRepository.findByUsername((username));
    if (existingUser.isPresent()) {
      throw new RuntimeException("이미 존재하는 사용자명입니다.");
    }

    User user = toEntity(userDTO);
    user.setJoinDate(LocalDate.now());
    user.setRole(UserRole.USER);
    User saveUser = userRepository.save(user);
    toDTO(saveUser);
  }

  @Transactional(readOnly = true)
  @Override
  public boolean existsByUsername(String username) {
    return userRepository.findByUsername((username)).isPresent();
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("User " + username + " not found");
    }
    return new CustomUserDetails(userOptional.get());
  }
}
