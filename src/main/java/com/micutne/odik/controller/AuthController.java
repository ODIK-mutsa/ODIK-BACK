package com.micutne.odik.controller;

import com.micutne.odik.domain.user.dto.LoginRequest;
import com.micutne.odik.domain.user.dto.LoginResponse;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("signup")
    public UserResponse signup(@RequestBody SignUpRequest request) {
        return authService.signup(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);

    }

    @PostMapping("refresh")
    public LoginResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }


}
