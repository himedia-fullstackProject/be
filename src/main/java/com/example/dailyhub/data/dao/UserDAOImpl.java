package com.example.dailyhub.data.dao;

import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

  private final UserRepository userRepository;

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public User addUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername((username));
  }
}
