package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
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


    Boolean existsByUserIdxAndState(User user, String state);

    default TourCourse findByUserIdxAndStateOrThrow(User user, String state) {
        return findByUserIdxAndState(user, state)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_COURSE_NOT_FOUND));
    }

    Optional<TourCourse> findByUserIdxAndState(User user, String state);

}



