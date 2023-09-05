package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourCourseRepository extends JpaRepository<TourCourse, Integer> {
    default TourCourse findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_COURSE_NOT_FOUND));
    }

    Optional<TourCourse> findByIdx(int idx);

    default TourCourse findByIdxAndStateOrThrow(int idx, String state) {
        return findByIdxAndState(idx, state)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_COURSE_NOT_FOUND));
    }

    Optional<TourCourse> findByIdxAndState(int idx, String state);

    Boolean existsByIdx(int idx);


    Boolean existsByUserIdxAndState(User user, String state);

    Boolean existsByIdxAndState(int idx, String state);

    default TourCourse findByUserIdxAndStateOrThrow(User user, String state) {
        return findByUserIdxAndState(user, state)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_COURSE_NOT_FOUND));
    }

    Optional<TourCourse> findByUserIdxAndState(User user, String state);

    Page<TourCourse> findAllByStateAndTitleContaining(String state, String title, Pageable pageable);

}



