package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.like.HistoryLikeTourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface HistoryLikeTourItemRepository extends JpaRepository<HistoryLikeTourItem, Integer> {
    default HistoryLikeTourItem findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
    }

    Optional<HistoryLikeTourItem> findByIdx(int idx);

    Boolean existsByIdx(int idx);
}
