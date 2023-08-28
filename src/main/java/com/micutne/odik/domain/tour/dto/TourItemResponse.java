package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TourItemResponse {

    String title;
    User user;

    Double location_lat;
    Double location_lng;

    String state;

    String address;
    String result;
    String reference_id_google;

    String phone_number;
    Float point_google;

    List<ImageTourItem> images_google;

    public static TourItemResponse fromEntity(TourItem tourItem) {
        TourItemResponse response = new TourItemResponse();
        response.title = tourItem.getTitle();
        response.user = tourItem.getUser();
        response.location_lat = tourItem.getLocationLat();
        response.location_lng = tourItem.getLocationLng();
        response.state = tourItem.getState();
        response.address = tourItem.getAddress();
        response.reference_id_google = tourItem.getReferenceIdGoogle();
        response.phone_number = tourItem.getPhoneNumber();
        response.point_google = tourItem.getPointGoogle();
        //response.images_google = tourItem.getImagesGoogle();
        return response;
    }


    public TourItemResponse (String result) {
        this.result = result;

    }

}
