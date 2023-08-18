package com.micutne.odik.service;

import com.micutne.odik.common.auth.CustomUserDetails;
import com.micutne.odik.common.auth.JwtTokenProvider;
import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.UserPrivate;
import com.micutne.odik.domain.user.dto.LoginRequest;
import com.micutne.odik.domain.user.dto.LoginResponse;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.repository.UserPrivateRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserPrivateRepository userPrivateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse signup(SignUpRequest request) {
        String userId = request.getId() + "&lt=" + request.getLoginType();
        if (!userRepository.existsById(request.getId() + "&lt=" + request.getLoginType())) {
            request.setId(userId);
            request.setPss(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(User.fromDto(request));
            UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
            return UserResponse.fromEntity(user);
        }
        throw new AuthException(ErrorCode.USER_ALREADY_EXIST);
    }

    public void signUpOAuth(SignUpRequest request) {
        String userId = request.getId() + "&lt=" + request.getLoginType();
        request.setId(userId);

        User user = userRepository.save(User.fromDto(request));
        UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
    }

    public LoginResponse login(LoginRequest request) {
        String userId = request.getId() + "&lt=email";
        User user = userRepository.findByIdOrThrow(userId);
        UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
        if (!passwordEncoder.matches(request.getPassword(), userPrivate.getPss())) {
            throw new AuthException(ErrorCode.AUTH_PASSWORD_UNEQUAL);
        }
        CustomUserDetails userDetails = CustomUserDetails.fromEntity(user);

        return getTokens(userDetails);
    }

    /**
     * refreshToken 으로 Token 재발행
     */
    public LoginResponse refresh(String token) {
        if (jwtTokenProvider.validate(token)) {
            String userId = jwtTokenProvider.parseClaims(token).getSubject();
            User user = userRepository.findByIdOrThrow(userId);

            CustomUserDetails userDetails = CustomUserDetails.fromEntity(user);

            return getTokens(userDetails);
        }
        throw new AuthException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }


    public LoginResponse getTokens(CustomUserDetails userDetails) {
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return new LoginResponse(accessToken, refreshToken);
    }
}
