package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewItemRequest {
    int rating;
    String content;
    User user_idx;
    TourItem tour_item_idx;

    @Builder
    public ReviewItemRequest(int rating, String content, User user_idx, TourItem tour_item_idx) {
        this.rating = rating;
        this.content = content;
        this.user_idx = user_idx;
        this.tour_item_idx = tour_item_idx;
    }
}
