package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.dto.item.TourItemResponse;
import lombok.Data;

@Data
public class TourCourseItemResponse {
    int idx;
    TourItemResponse tour_item;
    int level;
    int day;

    public static TourCourseItemResponse fromEntity(TourCourseListTourItem itemList) {
        TourCourseItemResponse response = new TourCourseItemResponse();
        response.idx = itemList.getIdx();
        response.tour_item = TourItemResponse.fromEntity(itemList.getTourItem());
        response.level = itemList.getLevel();
        response.day = itemList.getDay();
        return response;
    }

}
