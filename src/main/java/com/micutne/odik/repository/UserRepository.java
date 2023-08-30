package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    default User findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findByIdx(int idx);

    default User findByTokenOrThrow(String token) {
        return findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findByToken(String token);

    default User findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findById(String id);

    Boolean existsById(String id);

    Boolean existsByNickName(String nickName);

}
