package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TourItemRequest {
    String title;
    User user;

    Double location_lat;
    Double location_lng;
    String state;
    String address;
    String reference_id_google;

    String phone_number;
    Float point_google;

    List<String> images_google;


}
