package com.micutne.odik.domain.review.dto.course;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ReviewCoursePageResultResponse {
    String result;
    Page<ReviewCourseResponse> review_course;

    public static ReviewCoursePageResultResponse fromPage(String result, Page<ReviewCourseResponse> review_course) {
        ReviewCoursePageResultResponse response = new ReviewCoursePageResultResponse();
        response.result = result;
        response.review_course = review_course;
        return response;
    }

    public static ReviewCoursePageResultResponse fromPage(String result) {
        ReviewCoursePageResultResponse response = new ReviewCoursePageResultResponse();
        response.result = result;
        return response;
    }
}
