package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.review.ReviewCourse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.Data;

@Data
public class ReviewTourCourseResponse {
    int rating;
    String content;
    ProfileResponse user;

    public static ReviewTourCourseResponse fromEntity(ReviewCourse reviewCourse) {
        ReviewTourCourseResponse response = new ReviewTourCourseResponse();
        response.rating = reviewCourse.getRating();
        response.content = reviewCourse.getContent();
        response.user = ProfileResponse.fromEntity(reviewCourse.getUser());
        return response;
    }
}
