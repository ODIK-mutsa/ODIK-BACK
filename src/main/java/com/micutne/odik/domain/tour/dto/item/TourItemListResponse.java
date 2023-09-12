package com.micutne.odik.domain.tour.dto.item;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class TourItemListResponse {
    User user;

    Double location_lat;

    Double location_lng;

    String state;
    String address;
    String type;

}
