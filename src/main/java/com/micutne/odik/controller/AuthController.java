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

    //회원 가입
    @PostMapping("signup")
    public UserResultResponse signup(@RequestBody SignUpRequest request) {
        return authService.signup(request);
    }

    //로그인
    @PostMapping("login")
    public UserResultResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);

    }

    //토큰 인증
    @GetMapping("validate_token")
    public UserResultResponse checkResponse(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
                                            Authentication authentication) {

        return authService.checkAuth(authorizationHeader, authentication);
    }

    //이메일 인증 - 메일 전송
    @PostMapping("email_verify/request")
    public EmailResponse emailRequest(@RequestBody EmailRequest request) {
        return emailService.emailVerifyRequest(request);
    }

    //이메일 인증 - 인증번호 확인
    @PostMapping("email_verify/verify")
    public EmailResponse emailVerify(@RequestBody EmailRequest request) {
        return emailService.verifyCheck(request);
    }

    //비밀번호 찾기 - 메일 전송
    @PostMapping("find_pw/request")
    public EmailResponse passwordRequest(@RequestBody EmailRequest request) {
        return emailService.passwordVerifyRequest(request);
    }

    //비밀번호 찾기 - 인증번호 확인
    @PostMapping("find_pw/verify")
    public EmailResponse passwordVerify(@RequestBody EmailRequest request) {
        return emailService.verifyCheck(request);
    }

    //비밀번호 찾기 - 비밀번호 변경(기존 비밀번호 필요 없음)
    @PutMapping("find_pw")
    public UserResultResponse passwordFind(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String password = requestData.get("password");
        return authService.changePasswordNoOld(email, password);
    }

    //비밀번호 찾기 - 기존의 비밀번호를 알고 있는 경우
    @PutMapping("change_pw")
    public UserResultResponse passwordChange(@RequestBody PasswordRequest request) {
        return authService.changePasswordHaveOld(request);
    }


}
