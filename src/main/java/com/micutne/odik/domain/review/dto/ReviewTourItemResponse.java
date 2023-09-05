package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

@Data
public class ReviewTourItemResponse {
    int idx;
    float rating;
    String content;
    ProfileResponse user;
    String date_join;

    public static ReviewTourItemResponse fromEntity(ReviewTourItem reviewTourItem) {
        ReviewTourItemResponse response = new ReviewTourItemResponse();
        response.idx = reviewTourItem.getIdx();
        response.rating = reviewTourItem.getRating();
        response.content = reviewTourItem.getContent();
        response.user = ProfileResponse.fromEntity(reviewTourItem.getUser());
        response.date_join = TimeUtils.getLocalTime(reviewTourItem.getDateCreate());

        return response;
    }

}
