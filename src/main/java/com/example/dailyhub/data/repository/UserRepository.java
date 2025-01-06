package com.example.dailyhub.data.repository;

import com.example.dailyhub.data.entity.User;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.username= :username")
  Optional<User> findByUsername(@Size(max = 255) String username);

  @Query("SELECT u.id FROM User u WHERE u.username = :username")
  Optional<Long> findUserIdByUsername(@Param("username") String username);

  @Query("SELECT u.username FROM User u WHERE u.id = :id")
  Optional<String> findByUserId(@Param("id") Long id);
<<<<<<< HEAD
}
=======
}
>>>>>>> 8294c4c5a484f55afd5ce0d1e2bccbd7edef584c
