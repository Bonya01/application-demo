package com.example.library.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.library.dto.UserDto;
import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(UserDto dto) {
        User user = new User(null,
                dto.getFullName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getStudentId(),
                null);

        Role studentRole = roleRepository.findByName("ROLE_STUDENT").orElseGet(() ->
                roleRepository.save(new Role(null, "ROLE_STUDENT"))
        );
        user.getRoles().add(studentRole);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateProfile(Long id, UserDto dto) {
        User user = findById(id);
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setStudentId(dto.getStudentId());
        return userRepository.save(user);
    }
}
