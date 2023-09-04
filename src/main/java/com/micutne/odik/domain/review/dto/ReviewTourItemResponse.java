package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTourItemResponse {
    int idx;
    int rating;
    String content;
    ProfileResponse user_idx;
    TourItemResponse tour_item_idx;
    String result;

    public static ReviewTourItemResponse fromEntity(ReviewTourItem reviewTourItem) {
        ReviewTourItemResponse response = new ReviewTourItemResponse();
        response.idx = reviewTourItem.getIdx();
        response.rating = reviewTourItem.getRating();
        response.content = reviewTourItem.getContent();
        response.user_idx = ProfileResponse.fromEntity(reviewTourItem.getUserIdx());
        response.tour_item_idx = TourItemResponse.fromEntity(reviewTourItem.getTourItemIdx());
        response.result = "OK";

        return response;
    }

    public ReviewTourItemResponse(String result) {
        this.result = result;
    }
}
