package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.review.ReviewCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewTourCourseRepository extends JpaRepository<ReviewCourse, Integer> {
    default ReviewCourse findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_COURSE_NOT_FOUND));
    }

    Optional<ReviewCourse> findByIdx(int idx);


    Page<ReviewCourse> findAll(Pageable pageable);


}
