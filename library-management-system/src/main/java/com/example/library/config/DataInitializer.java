package com.example.library.config;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;

@Configuration
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            Role admin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
            Role student = roleRepository.findByName("ROLE_STUDENT").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_STUDENT")));

            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User adminUser = new User(null, "Administrator", "admin@example.com", new BCryptPasswordEncoder().encode("admin123"), null, null);
                HashSet<Role> roles = new HashSet<>();
                roles.add(admin);
                adminUser.setRoles(roles);
                userRepository.save(adminUser);
            }
        };
    }
}
