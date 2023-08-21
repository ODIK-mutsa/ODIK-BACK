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


    public UserResponse readOne(String userId) {
        return UserResponse.fromEntity(userRepository.findByIdOrThrow(userId));
    }

    /**
     * 사용자 프로필 찾기
     */
    public ProfileResponse readProfile(String userToken) {
        return ProfileResponse.fromEntity(userRepository.findByTokenOrThrow(userToken));
    }

    /**
     * 사용자 정보 수정하기
     */
    @Transactional
    public UserResponse updateInfo(UserRequest userRequest, String id) {
        User user = userRepository.findByIdOrThrow(id);
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
