package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.images.ImageReviewTourItem;
import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

import java.util.List;

@Data
public class ReviewTourItemResponse {
    int idx;
    float rating;
    String content;
    ProfileResponse user;
    String date_join;
    List<String> images;

    public static ReviewTourItemResponse fromEntity(ReviewTourItem reviewTourItem) {
        ReviewTourItemResponse response = new ReviewTourItemResponse();
        response.idx = reviewTourItem.getIdx();
        response.rating = reviewTourItem.getRating();
        response.content = reviewTourItem.getContent();
        response.user = ProfileResponse.fromEntity(reviewTourItem.getUser());
        response.date_join = TimeUtils.getLocalTime(reviewTourItem.getDateCreate());
        response.images = reviewTourItem.getReviewImage().stream().map(ImageReviewTourItem::getUrl).toList();
        return response;
    }

}
