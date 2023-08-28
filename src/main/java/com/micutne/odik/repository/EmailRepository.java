package com.micutne.odik.repository;

import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.common.exception.InvalidValueException;
import com.micutne.odik.domain.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    default Email findByTokenOrThrow(String token) {
        return findByToken(token).orElseThrow(() -> new InvalidValueException(ErrorCode.EMAIL_TOKEN_NOT_FOUND));
    }

    Optional<Email> findByToken(String token);

    default Email findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new InvalidValueException(ErrorCode.EMAIL_TOKEN_NOT_FOUND));
    }

    Optional<Email> findByEmail(String email);

    Boolean existsByToken(String token);

    Boolean existsByEmail(String email);

    List<Email> findByDateBefore(LocalDateTime dateTime);
}
