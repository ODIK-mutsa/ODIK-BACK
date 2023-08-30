package com.micutne.odik.repository;

import com.micutne.odik.domain.tour.TourCourseListTourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCourseListTourItemRepository extends JpaRepository<TourCourseListTourItem, Integer> {
}
