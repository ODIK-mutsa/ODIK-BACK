package com.micutne.odik.repository;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.TourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCourseListTourItemRepository extends JpaRepository<TourCourseListTourItem, Integer> {
    Boolean existsByTourCourseAndTourItem(TourCourse tourCourse, TourItem tourItem);
}
