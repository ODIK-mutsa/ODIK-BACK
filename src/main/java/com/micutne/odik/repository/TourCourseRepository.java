package com.micutne.odik.repository;

import com.micutne.odik.domain.tour.TourCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCourseRepository extends JpaRepository<TourCourse, Long> {


}



