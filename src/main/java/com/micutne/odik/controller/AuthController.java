package com.micutne.odik.controller;

import com.micutne.odik.domain.email.dto.EmailRequest;
import com.micutne.odik.domain.email.dto.EmailResponse;
import com.micutne.odik.domain.user.dto.LoginRequest;
import com.micutne.odik.domain.user.dto.PasswordRequest;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserResultResponse;
import com.micutne.odik.service.AuthService;
import com.micutne.odik.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("signup")
    public UserResultResponse signup(@RequestBody SignUpRequest request) {
        return authService.signup(request);
    }

    @PostMapping("login")
    public UserResultResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);

    }

    @GetMapping("validate_token")
    public UserResultResponse checkResponse(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
                                            Authentication authentication) {

        return authService.checkAuth(authorizationHeader, authentication);
    }

    @PostMapping("email_verify/request")
    public EmailResponse emailRequest(@RequestBody EmailRequest request) {
        return emailService.emailVerifyRequest(request);
    }

    @PostMapping("email_verify/verify")
    public EmailResponse emailVerify(@RequestBody EmailRequest request) {
        return emailService.verifyCheck(request);
    }

    @PostMapping("find_pw/request")
    public EmailResponse passwordRequest(@RequestBody EmailRequest request) {
        return emailService.passwordVerifyRequest(request);
    }

    @PostMapping("find_pw/verify")
    public EmailResponse passwordVerify(@RequestBody EmailRequest request) {
        return emailService.verifyCheck(request);
    }

    @PutMapping("find_pw")
    public UserResultResponse passwordFind(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String password = requestData.get("password");
        return authService.findPassword(email, password);
    }

    /**
     * 기존의 비밀번호를 알고 있는 경우
     */
    @PutMapping("change_pw")
    public UserResultResponse passwordChange(@RequestBody PasswordRequest request) {
        return authService.changePassword(request);
    }


}
