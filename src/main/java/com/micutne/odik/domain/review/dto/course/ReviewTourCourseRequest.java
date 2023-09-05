package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewTourCourseRequest {
    int rating;
    int tour_course_idx;
    String content;
    User user;
    TourCourse tourCourse;

}
