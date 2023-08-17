package com.micutne.odik.domain.tour.dto;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class TourItemResponse {
    String title;
    User userIdx;

    Double locationLat;
    Double locationLng;


}
