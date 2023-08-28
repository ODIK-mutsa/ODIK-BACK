package com.micutne.odik.domain.tour.dto;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class TourItemListResponse {
    String title;
    User userIdx;

    Double locationLat;
    Double locationLng;

    String locationLabelLv1;
    String locationLabelLv2;
    String locationLabelLv3;


}
