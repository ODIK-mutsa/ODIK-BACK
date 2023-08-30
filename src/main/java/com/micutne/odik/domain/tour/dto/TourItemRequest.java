package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserResponse;
import lombok.Builder;
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

    //List<ImageTourItem> images_google;
    List<String> images_google;

    //String images_google;

    @Builder
    public TourItemRequest(String title, User user, Double location_lat, Double location_lng, String state, String address, String reference_id_google, String phone_number, Float point_google, List<String> images_google) {
        this.title = title;
        this.user = user;
        this.location_lat = location_lat;
        this.location_lng = location_lng;
        this.state = state;
        this.address = address;
        this.reference_id_google = reference_id_google;
        this.phone_number = phone_number;
        this.point_google = point_google;
        this.images_google = images_google;
        //this.images_google = images_google;

    }
    public void updateUser(User user) {
        this.user = user;
    }


}
