package com.micutne.odik.domain.like.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ItemLikeRequest {
    int tour_item_idx;
    TourItem tourItem;
    User user;

    public ItemLikeRequest() {
        this.tour_item_idx = -1;
    }
}
