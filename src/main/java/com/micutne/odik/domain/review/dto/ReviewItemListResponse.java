package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.Data;

@Data
public class ReviewItemListResponse {
    int idx;
    int rating;
    String content;
    ProfileResponse user_idx;
    TourItem tour_item_idx;
}
