package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.library.dto.UserDto;
import com.example.library.entity.User;
import com.example.library.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        try {
            User user = userService.register(dto);
            return "redirect:/login?registered";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }
}
