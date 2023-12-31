package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.like.HistoryLikeTourCourse;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryLikeTourCourseRepository extends JpaRepository<HistoryLikeTourCourse, Integer> {

    default HistoryLikeTourCourse findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
    }

    Optional<HistoryLikeTourCourse> findByIdx(int idx);

    Boolean existsByIdx(int idx);

    default HistoryLikeTourCourse findByTourCourseAndUserOrThrow(TourCourse tourCourse, User user) {
        return findByTourCourseAndUser(tourCourse, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
    }

    Optional<HistoryLikeTourCourse> findByTourCourseAndUser(TourCourse tourCourse, User user);

    Boolean existsByTourCourseAndUser(TourCourse tourCourse, User user);

    int countByTourCourse(TourCourse tourCourse);

    List<HistoryLikeTourCourse> findAllByUser(User user, Pageable pageable);


}
