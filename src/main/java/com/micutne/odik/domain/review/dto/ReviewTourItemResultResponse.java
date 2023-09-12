package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import lombok.Data;

@Data
public class ReviewTourItemResultResponse {
    String result;
    ReviewTourItemResponse review_tour_item;

    public static ReviewTourItemResultResponse fromEntity(ReviewTourItem reviewTourItem, String result) {
        ReviewTourItemResultResponse response = new ReviewTourItemResultResponse();
        response.review_tour_item = ReviewTourItemResponse.fromEntity(reviewTourItem);
        response.result = result;
        return response;
    }

    public static ReviewTourItemResultResponse fromEntity(String result) {
        ReviewTourItemResultResponse response = new ReviewTourItemResultResponse();
        response.result = result;
        return response;
    }
}
