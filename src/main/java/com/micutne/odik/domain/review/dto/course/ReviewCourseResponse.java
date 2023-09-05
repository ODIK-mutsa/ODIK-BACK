package com.micutne.odik.domain.review.dto.course;

import com.micutne.odik.domain.review.ReviewTourCourse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

@Data
public class ReviewCourseResponse {
    int idx;
    float rating;
    String date_join;
    String content;
    ProfileResponse user;

    public static ReviewCourseResponse fromEntity(ReviewTourCourse reviewCourse) {
        ReviewCourseResponse response = new ReviewCourseResponse();
        response.rating = reviewCourse.getRating();
        response.content = reviewCourse.getContent();
        response.user = ProfileResponse.fromEntity(reviewCourse.getUser());
        response.idx = reviewCourse.getIdx();
        response.date_join = TimeUtils.getLocalTime(reviewCourse.getDateCreate());
        return response;
    }
}
