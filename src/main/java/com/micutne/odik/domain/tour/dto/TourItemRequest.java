package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class TourItemRequest {
    String title;
    User user;

    Double locationLat;
    Double locationLng;

    String locationLabelLv1;
    String locationLabelLv2;
    String locationLabelLv3;


    @Builder
    public TourItemRequest(String title, User user, Double locationLat, Double locationLng, String locationLabelLv1, String locationLabelLv2, String locationLabelLv3) {
        this.title = title;
        this.user = user;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.locationLabelLv1 = locationLabelLv1;
        this.locationLabelLv2 = locationLabelLv2;
        this.locationLabelLv3 = locationLabelLv3;
    }


}
