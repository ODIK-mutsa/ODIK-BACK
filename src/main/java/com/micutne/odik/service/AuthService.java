package com.micutne.odik.service;

import com.micutne.odik.common.auth.CustomUserDetails;
import com.micutne.odik.common.auth.JwtTokenProvider;
import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.UserPrivate;
import com.micutne.odik.domain.user.dto.LoginRequest;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.domain.user.dto.VaildResponse;
import com.micutne.odik.repository.UserPrivateRepository;
import com.micutne.odik.repository.UserRepository;
import com.micutne.odik.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
    public UserResponse signup(SignUpRequest request) {
        String userId = FormatUtils.formatId(request.getId(), request.getLogin_type());
        if (!userRepository.existsById(userId)) {
            request.setId(userId);
            request.setPss(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(User.fromDto(request));
            user.setToken(jwtTokenProvider.generateToken(CustomUserDetails.fromEntity(user)));
            UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
            return UserResponse.fromEntity(user);
        }
        throw new AuthException(ErrorCode.USER_ALREADY_EXIST);
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
    public VaildResponse login(LoginRequest request) {
        String userId = FormatUtils.formatId(request.getId(), "email");
        if (userRepository.existsById(userId)) {
            User user = userRepository.findByIdOrThrow(userId);
            UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
            if (!passwordEncoder.matches(request.getPassword(), userPrivate.getPss())) {
                return new VaildResponse("NOT_FOUND");
            }
            return VaildResponse.fromEntity(user, "OK");
        } else return new VaildResponse("NOT_FOUND");
    }

    public VaildResponse checkAuth(String token, Authentication authentication) {
        if (token == null || token.isEmpty() || authentication == null) {
            return new VaildResponse("INVALID");
        }

        String userId = authentication.getPrincipal().toString();
        User user = userRepository.findByIdOrThrow(userId);
        if (user.getToken().equals(token.split(" ")[1])) {
            return VaildResponse.fromEntity(user, "VALID");
        }
        return new VaildResponse("INVALID");
    }

    @Transactional
    public Map<String, String> changePassword(String email, String newPassword) {
        String userId = FormatUtils.formatId(email, "email");
        log.info(userId);
        User user = userRepository.findByIdOrThrow(userId);
        UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
        userPrivate.updatePassword(passwordEncoder.encode(newPassword));

        Map<String, String> result = new HashMap<>();
        result.put("result", "OK");
        return result;
    }


}
