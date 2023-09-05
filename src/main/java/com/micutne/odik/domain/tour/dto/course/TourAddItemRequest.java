package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TourAddItemRequest {
    int tour_course_idx;
    String title;
    String state;
    List<TourUpdateItemRequest> tour_items;
    TourCourse tourCourse;
    List<TourItem> tourItems;
}
