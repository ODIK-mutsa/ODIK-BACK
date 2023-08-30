package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourAddItemRequest {
    String title;
    int tour_course_idx;
    int tour_item_idx;
    int level;
    TourCourse tourCourse;
    TourItem tourItem;
}
