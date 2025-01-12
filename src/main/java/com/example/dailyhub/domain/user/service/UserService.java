package com.example.dailyhub.domain.user.service;

import com.example.dailyhub.domain.user.dto.UserDTO;
import com.example.dailyhub.domain.user.entity.User;

public interface UserService {

    UserDTO getUser(Long id);

    void addUser(UserDTO userDTO);

    boolean existsByUsername(String username);

    default UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();
    }

    default User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .nickname(userDTO.getNickname())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .birthday(userDTO.getBirthday())
                .build();
    }
}
