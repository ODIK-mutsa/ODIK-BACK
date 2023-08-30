package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReviewItemResponse {
    int idx;
    int rating;
    String content;
    ProfileResponse user_idx;
    TourItem tour_item_idx;
    String result;

    public static ReviewItemResponse fromEntity(ReviewItem reviewItem) {
        ReviewItemResponse response = new ReviewItemResponse();
        response.idx = reviewItem.getIdx();
        response.rating = reviewItem.getRating();
        response.content = reviewItem.getContent();
        response.user_idx = ProfileResponse.fromEntity(reviewItem.getUserIdx());
        response.tour_item_idx = reviewItem.getTourItemIdx();
        response.result = "OK";

        return response;
    }
}
