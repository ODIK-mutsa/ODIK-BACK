package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.review.ReviewTourCourse;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewTourCourseRepository extends JpaRepository<ReviewTourCourse, Integer> {
    default ReviewTourCourse findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_COURSE_NOT_FOUND));
    }

    Optional<ReviewTourCourse> findByIdx(int idx);


    Page<ReviewTourCourse> findAll(Pageable pageable);

    Boolean existsByIdx(int idx);

    Boolean existsByTourCourseAndUser(TourCourse tourCourse, User user);

    Page<ReviewTourCourse> findAllByTourCourse(TourCourse tourCourse, Pageable pageable);

    Page<ReviewTourCourse> findAllByUser(User user, Pageable pageable);
}
