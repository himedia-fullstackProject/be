package com.example.dailyhub.data.dao;

import com.example.dailyhub.data.entity.User;
import java.util.Optional;

public interface UserDAO {

  User getUser(Long id);

  User addUser(User user);

  Optional<User> findByUsername(String username);

}
