package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import lombok.Data;

@Data
public class TourCourseItemResponse {
    int idx;
    String title;
    TourItemResponse tour_item;
    int level;

    public static TourCourseItemResponse fromEntity(TourCourseListTourItem itemList) {
        TourCourseItemResponse response = new TourCourseItemResponse();
        response.idx = itemList.getIdx();
        response.title = itemList.getTitle();
        response.tour_item = TourItemResponse.fromEntity(itemList.getTourItem());
        response.level = itemList.getLevel();
        return response;
    }

}
