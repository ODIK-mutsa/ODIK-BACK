package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.review.ReviewTourCourse;
import lombok.Data;

@Data
public class ReviewCourseResultResponse {
    String result;
    ReviewCourseResponse review_tour_course;

    public static ReviewCourseResultResponse fromEntity(ReviewTourCourse reviewCourse, String result) {
        ReviewCourseResultResponse response = new ReviewCourseResultResponse();
        response.review_tour_course = ReviewCourseResponse.fromEntity(reviewCourse);
        response.result = result;
        return response;
    }

    public static ReviewCourseResultResponse fromEntity(String result) {
        ReviewCourseResultResponse response = new ReviewCourseResultResponse();
        response.result = result;
        return response;
    }
}
