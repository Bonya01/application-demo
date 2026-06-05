package com.example.library.service;

import com.example.library.dto.UserDto;
import com.example.library.entity.User;

import java.util.Optional;

public interface UserService {
    User register(UserDto dto);
    Optional<User> findByEmail(String email);
    User findById(Long id);
    User updateProfile(Long id, UserDto dto);
}
