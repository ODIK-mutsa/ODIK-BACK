package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.like.HistoryLikeTourCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryLikeTourCourseRepository extends JpaRepository<HistoryLikeTourCourse, Integer> {

    default HistoryLikeTourCourse findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
    }

    Optional<HistoryLikeTourCourse> findByIdx(int idx);

    Boolean existsByIdx(int idx);
}
