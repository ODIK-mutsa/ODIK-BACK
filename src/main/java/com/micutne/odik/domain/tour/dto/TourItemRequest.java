package com.micutne.odik.domain.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
@AllArgsConstructor
public class TourItemRequest {
    String title;
    User userIdx;

    Double locationLat;
    Double locationLng;

    String locationLabelLv1;
    String locationLabelLv2;
    String locationLabelLv3;


}
