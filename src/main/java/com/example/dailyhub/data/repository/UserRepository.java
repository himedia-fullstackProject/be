package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    static Optional<Object> findByUsername(String userName) {
        return null;
    }
}
