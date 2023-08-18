package com.micutne.odik.service;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 사용자 정보 찾기
     */
    public UserResponse readOne(Long userIdx) {
        return UserResponse.fromEntity(userRepository.findByIdxOrThrow(userIdx));
    }

    public UserResponse readOne(String userName) {
        return UserResponse.fromEntity(userRepository.findByIdOrThrow(userName));
    }

    /**
     * 사용자 프로필 찾기
     */
    public ProfileResponse readProfile(Long userIdx) {
        return ProfileResponse.fromEntity(userRepository.findByIdxOrThrow(userIdx));
    }

    /**
     * 사용자 정보 수정하기
     */
    @Transactional
    public UserResponse updateInfo(UserRequest userRequest, String id) {
        User user = userRepository.findByIdOrThrow(id);
        log.info(id);
        user.updateInfo(userRequest);
        return UserResponse.fromEntity(user);
    }

    /**
     * 사용자 상태 수정하기
     */
    @Transactional
    public UserResponse updateState(UserRequest userRequest, String id) {
        User user = userRepository.findByIdOrThrow(id);
        user.updateState(userRequest);
        return UserResponse.fromEntity(user);
    }


}
