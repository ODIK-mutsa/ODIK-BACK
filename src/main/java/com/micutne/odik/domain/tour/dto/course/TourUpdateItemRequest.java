package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourItem;
import lombok.Data;

@Data
public class TourUpdateItemRequest {
    int idx;
    int level;
    int day;
    TourItem item;

    public TourUpdateItemRequest() {
        this.level = -1;
        this.day = -1;
    }
}
