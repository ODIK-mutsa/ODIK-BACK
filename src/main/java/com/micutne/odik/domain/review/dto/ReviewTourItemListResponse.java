package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.Data;

@Data
public class ReviewTourItemListResponse {
    int idx;
    int rating;
    String content;
    ProfileResponse user_idx;
    TourItemResponse tour_item_idx;
}
