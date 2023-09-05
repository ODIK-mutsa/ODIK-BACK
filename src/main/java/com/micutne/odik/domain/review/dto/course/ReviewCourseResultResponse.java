package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.review.ReviewCourse;
import lombok.Data;

@Data
public class ReviewCourseResultResponse {
    String result;
    ReviewTourCourseResponse review_tour_course;

    public static ReviewCourseResultResponse fromEntity(ReviewCourse reviewCourse, String result) {
        ReviewCourseResultResponse response = new ReviewCourseResultResponse();
        response.review_tour_course = ReviewTourCourseResponse.fromEntity(reviewCourse);
        response.result = result;
        return response;
    }

    public static ReviewCourseResultResponse fromEntity(String result) {
        ReviewCourseResultResponse response = new ReviewCourseResultResponse();
        response.result = result;
        return response;
    }
}
