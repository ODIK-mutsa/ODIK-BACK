package com.micutne.odik.repository;

import com.micutne.odik.domain.images.ImageReviewTourCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageReviewTourCourseRepository extends JpaRepository<ImageReviewTourCourse, Long> {
}
