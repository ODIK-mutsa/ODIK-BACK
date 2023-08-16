package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.user.UserPrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrivateRepository extends JpaRepository<UserPrivate, Long> {

    default UserPrivate findByIdxOrThrow(Long id) {
        return findByIdx(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_PRIVATE_NOT_FOUND));
    }

    Optional<UserPrivate> findByIdx(Long idx);
}
