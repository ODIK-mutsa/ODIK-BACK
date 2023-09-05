package com.micutne.odik.domain.like.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ItemLikeRequest {
    boolean like;
    TourItem tourItem;
    User user;
}
