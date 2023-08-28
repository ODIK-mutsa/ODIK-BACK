package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class TourItemResponse {
    String title;
    User user;

    Double locationLat;
    Double locationLng;


}
