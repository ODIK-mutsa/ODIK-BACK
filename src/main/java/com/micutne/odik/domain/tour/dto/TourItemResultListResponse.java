package com.micutne.odik.domain.tour.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
@Data
public class TourItemResultListResponse {
    String result;
    Page<TourItemResponse> tour_items;

    public static TourItemResultListResponse fromEntity(Page<TourItemResponse> tour_items, String result) {
        TourItemResultListResponse response = new TourItemResultListResponse();
        response.result = result;
        response.tour_items = tour_items;
        return response;
    }

    public static TourItemResultListResponse fromEntity(String result) {
        TourItemResultListResponse response = new TourItemResultListResponse();
        response.result = result;
        return response;
    }
}
