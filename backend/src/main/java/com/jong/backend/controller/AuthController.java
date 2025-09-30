package com.jong.backend.controller;

import com.jong.backend.dto.AuthRequest;
import com.jong.backend.dto.AuthResponse;
import com.jong.backend.dto.RefreshTokenRequest;
import com.jong.backend.dto.RegisterRequest;
import com.jong.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 회원가입 API
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인 API
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request
    ) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 토큰 갱신 API
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}