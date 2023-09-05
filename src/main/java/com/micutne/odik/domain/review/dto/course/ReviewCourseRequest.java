package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ReviewCourseRequest {
    float rating;
    int tour_course_idx;
    int review_course_idx;
    String content;
    User user;
    TourCourse tourCourse;

    public ReviewCourseRequest() {
        this.rating = -1;
        this.tour_course_idx = -1;
        this.review_course_idx = -1;
    }
}
