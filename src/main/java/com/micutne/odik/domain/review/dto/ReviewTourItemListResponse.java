package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewTourItemListResponse {
    int idx;
    int rating;
    String content;
    ProfileResponse user_idx;
    TourItemResponse tour_item_idx;
    String result;

    @Builder
    public ReviewTourItemListResponse(int idx, int rating, String content, ProfileResponse user_idx, TourItemResponse tour_item_idx) {
        this.idx = idx;
        this.rating = rating;
        this.content = content;
        this.user_idx = user_idx;
        this.tour_item_idx = tour_item_idx;
    }


}
