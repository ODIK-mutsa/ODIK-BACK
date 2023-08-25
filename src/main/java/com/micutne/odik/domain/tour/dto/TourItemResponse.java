package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class TourItemResponse {
    String title;
    User user;

    Double location_lat;
    Double location_lng;

    String state;

    String address;

/*
    public static TourItemResponse fromEntity(TourItem tourItem) {
        TourItemResponse response = new TourItemResponse();
        response.title = tourItem.getTitle();
        response.user = tourItem.getUser();
        response.location_lat = tourItem.getLocationLat();
        response.location_lng = tourItem.getLocationLng();
        response.state = tourItem.getState();
        response.address = tourItem.getAddress();
        return response;
    }

 */


}
