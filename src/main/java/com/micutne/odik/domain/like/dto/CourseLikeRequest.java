package com.micutne.odik.domain.like.dto;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class CourseLikeRequest {
    int tour_course_idx;
    TourCourse tourCourse;
    User user;

    public CourseLikeRequest() {
        this.tour_course_idx = -1;
    }
}
