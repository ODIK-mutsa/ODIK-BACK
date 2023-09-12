package com.micutne.odik.service;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserRequest;
import com.micutne.odik.domain.user.dto.UserResultResponse;
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


    public UserResultResponse readOne(String username) {
        if (!userRepository.existsById(username)) return UserResultResponse.fromEntity("USER_NOT_EXIST");
        return UserResultResponse.fromEntity(userRepository.findByIdOrThrow(username), "OK");
    }

    /**
     * 사용자 프로필 찾기
     */
    public ProfileResponse readProfile(int userIdx) {

        return ProfileResponse.fromEntity(userRepository.findByIdxOrThrow(userIdx));
    }

    /**
     * 사용자 정보 수정하기
     */
    @Transactional
    public UserResultResponse updateInfo(UserRequest userRequest, String username) {
        if (!userRepository.existsById(username)) return UserResultResponse.fromEntity("USER_NOT_EXIST");

        User user = userRepository.findByIdOrThrow(username);
        user.updateInfo(userRequest);
        return UserResultResponse.fromEntity(user, "OK");
    }

    /**
     * 사용자 상태 수정하기
     */
    @Transactional
    public UserResultResponse updateState(UserRequest userRequest, String username) {
        if (!userRepository.existsById(username)) return UserResultResponse.fromEntity("USER_NOT_EXIST");

        User user = userRepository.findByIdOrThrow(username);
        user.updateState(userRequest);
        return UserResultResponse.fromEntity(user, "OK");
    }

}
