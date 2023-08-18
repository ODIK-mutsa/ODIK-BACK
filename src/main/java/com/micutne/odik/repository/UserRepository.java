package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdxOrThrow(Long id) {
        return findByIdx(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findByIdx(Long id);

    default User findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findById(String id);

    Boolean existsById(String id);

    Boolean existsByNickName(String nickName);
}
