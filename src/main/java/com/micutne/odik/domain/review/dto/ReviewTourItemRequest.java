package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ReviewTourItemRequest {
    float rating;
    String content;
    User user;
    TourItem tourItem;
    int tour_item_idx;
    int review_tour_item_idx;

    public ReviewTourItemRequest() {
        this.rating = -1;
        this.tour_item_idx = -1;
        this.review_tour_item_idx = -1;
    }
}
