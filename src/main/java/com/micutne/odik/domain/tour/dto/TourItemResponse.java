package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.common.exception.BusinessException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourItemResponse {

    int idx;

    String title;
    ProfileResponse user;

    Double location_lat;
    Double location_lng;

    String state;

    String address;
    String result;
    String reference_id_google;

    String phone_number;
    Float point_google;
    String type;

    List<String> images_google;


    public static TourItemResponse fromEntity(TourItem tourItem) {
        TourItemResponse response = new TourItemResponse();
        response.idx = tourItem.getIdx();
        response.title = tourItem.getTitle();
        response.user = ProfileResponse.fromEntity(tourItem.getUser());
        response.location_lat = tourItem.getLocationLat();
        response.location_lng = tourItem.getLocationLng();
        response.state = tourItem.getState();
        response.address = tourItem.getAddress();
        response.reference_id_google = tourItem.getReferenceIdGoogle();
        response.phone_number = tourItem.getPhoneNumber();
        response.point_google = tourItem.getPointGoogle();
        response.type = tourItem.getType();

        if (tourItem.getImagesGoogle() != null)
            response.setImages_google(tourItem.getImagesGoogle().stream().map(ImageTourItem::getImagesGoogle).toList());
        response.result = "OK";
        return response;
    }

    public static TourItemResponse fromEntity(TourItem tourItem, List<ImageTourItem> imageTourItems) {

        TourItemResponse response = fromEntity(tourItem);
        response.setImages_google(imageTourItems.stream().map(ImageTourItem::getImagesGoogle).toList());
        return response;
    }


    public TourItemResponse(String result) {
        this.result = result;

    }

    public static TourItemResponse alreadyExist(String result) {
        TourItemResponse response = new TourItemResponse();
        response.result = result;
        return response;
    }

    public TourItemResponse(ErrorCode errorCode) {
        this.result = String.valueOf(new BusinessException(ErrorCode.TOUR_ITEM_ALREADY_EXIST));
    }

}
