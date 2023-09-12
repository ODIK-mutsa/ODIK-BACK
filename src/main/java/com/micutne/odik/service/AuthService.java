package com.micutne.odik.service;

import com.micutne.odik.common.auth.CustomUserDetails;
import com.micutne.odik.common.auth.JwtTokenProvider;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.UserPrivate;
import com.micutne.odik.domain.user.dto.LoginRequest;
import com.micutne.odik.domain.user.dto.PasswordRequest;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserResultResponse;
import com.micutne.odik.repository.UserPrivateRepository;
import com.micutne.odik.repository.UserRepository;
import com.micutne.odik.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserPrivateRepository userPrivateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, UserPrivateRepository userPrivateRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userPrivateRepository = userPrivateRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public UserResultResponse signup(SignUpRequest request) {
        String userId = FormatUtils.formatId(request.getId(), request.getLogin_type());
        if (!userRepository.existsById(userId)) {
            request.setId(userId);
            request.setPss(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(User.fromDto(request));
            user.setToken(jwtTokenProvider.generateToken(CustomUserDetails.fromEntity(user)));
            UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
            return UserResultResponse.fromEntity(user, "OK");
        }
        return UserResultResponse.fromEntity("ALREADY_EXIST");
    }

    @Transactional
    public void signUpOAuth(SignUpRequest request) {
        String userId = FormatUtils.formatId(request.getId(), request.getLogin_type());
        request.setId(userId);
        User user = userRepository.save(User.fromDto(request));
        user.setToken(jwtTokenProvider.generateToken(CustomUserDetails.fromEntity(user)));
        UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
    }

    @Transactional
    public UserResultResponse login(LoginRequest request) {
        String userId = FormatUtils.formatId(request.getId(), "email");
        if (userRepository.existsById(userId)) {
            User user = userRepository.findByIdOrThrow(userId);
            UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
            if (!passwordEncoder.matches(request.getPassword(), userPrivate.getPss())) {
                return UserResultResponse.fromEntity("NOT_FOUND");
            }
            return UserResultResponse.fromEntity(user, "OK");
        } else return UserResultResponse.fromEntity("NOT_FOUND");
    }

    public UserResultResponse checkAuth(String token, Authentication authentication) {
        if (token == null || token.isEmpty() || authentication == null) {
            return UserResultResponse.fromEntity("INVALID");
        }

        String userId = authentication.getPrincipal().toString();
        User user = userRepository.findByIdOrThrow(userId);
        if (user.getToken().equals(token.split(" ")[1])) {
            return UserResultResponse.fromEntity(user, "VALID");
        }
        return UserResultResponse.fromEntity("INVALID");
    }

    @Transactional
    public UserResultResponse findPassword(String email, String newPassword) {
        String userId = FormatUtils.formatId(email, "email");
        User user = userRepository.findByIdOrThrow(userId);
        UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
        userPrivate.updatePassword(passwordEncoder.encode(newPassword));

        return UserResultResponse.fromEntity("OK");
    }


    public UserResultResponse changePassword(PasswordRequest request) {
        if (userRepository.existsById(request.getId())) {
            User user = userRepository.findByIdOrThrow(request.getId());
            UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
            if (!passwordEncoder.matches(request.getPassword_old(), userPrivate.getPss())) {
                return UserResultResponse.fromEntity("INVALID");
            }
            userPrivate.updatePassword(passwordEncoder.encode(request.getPassword_new()));
            return UserResultResponse.fromEntity("VALID");
        }
        return UserResultResponse.fromEntity("INVALID");
    }
}
