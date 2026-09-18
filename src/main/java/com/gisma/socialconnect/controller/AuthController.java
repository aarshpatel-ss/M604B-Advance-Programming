package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.LoginRequest;
import com.gisma.socialconnect.dto.UserResponse;
import com.gisma.socialconnect.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Login. Signing up reuses POST /api/v1/users.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }
}
