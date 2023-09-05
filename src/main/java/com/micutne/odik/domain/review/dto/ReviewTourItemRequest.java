package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
public class ReviewTourItemRequest {
    float rating;
    String content;
    User user;
    TourItem tourItem;
    int tour_item_idx;
    int review_tour_item_idx;
/*
    @Builder
    public ReviewTourItemRequest(float rating, String content, TourItem tour_item_idx) {
        this.rating = rating;
        this.content = content;
       // this.user_idx = user_idx;
        this.tour_item_idx = tour_item_idx;
    }

 */

    public ReviewTourItemRequest() {
        this.rating = -1;
        this.tour_item_idx = -1;
        this.review_tour_item_idx = -1;
    }
}
