package com.micutne.odik.service;

import com.micutne.odik.common.auth.CustomUserDetails;
import com.micutne.odik.common.auth.JwtTokenProvider;
import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.UserPrivate;
import com.micutne.odik.domain.user.dto.*;
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

    public UserResponse signup(SignUpRequest request) {
        String userId = FormatUtils.formatId(request.getId(), request.getLogin_type());
        if (!userRepository.existsById(userId)) {
            request.setId(userId);
            request.setPss(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(User.fromDto(request));
            UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
            return UserResponse.fromEntity(user);
        }
        throw new AuthException(ErrorCode.USER_ALREADY_EXIST);
    }

    public void signUpOAuth(SignUpRequest request) {
        String userId = FormatUtils.formatId(request.getId(), request.getLogin_type());
        request.setId(userId);

        User user = userRepository.save(User.fromDto(request));
        UserPrivate userPrivate = userPrivateRepository.save(UserPrivate.fromDto(request, user));
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        String userId = FormatUtils.formatId(request.getId(), "email");
        if (userRepository.existsById(userId)) {
            User user = userRepository.findByIdOrThrow(userId);
            UserPrivate userPrivate = userPrivateRepository.findByIdxOrThrow(user.getIdx());
            if (!passwordEncoder.matches(request.getPassword(), userPrivate.getPss())) {
                return new LoginResponse("NOT_FOUND");
            }
            user.setToken(jwtTokenProvider.generateToken(CustomUserDetails.fromEntity(user)));

            return LoginResponse.fromEntity(user, "OK");
        } else return new LoginResponse("NOT_FOUND");
    }

    public CheckResponse checkAuth(String token, Authentication authentication) {
        String userId = authentication.getPrincipal().toString();
        User user = userRepository.findByIdOrThrow(userId);
        if (user.getToken().equals(token.split(" ")[1])) {
            return new CheckResponse("VALID");
        }
        return new CheckResponse("INVALID");
    }


    public String getTokens(CustomUserDetails userDetails) {
        return jwtTokenProvider.generateToken(userDetails);
    }


}
