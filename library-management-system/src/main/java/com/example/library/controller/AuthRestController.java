package com.example.library.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        // Security is disabled in this build — return a simple acknowledgement.
        return ResponseEntity.ok(Map.of("message", "security_disabled"));
    }
}
