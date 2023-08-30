package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class TourItemListResponse {
    String title;

    User user;

    Double location_lat;

    Double location_lng;

    String state;
    String address;



    }
