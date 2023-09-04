package com.micutne.odik.domain.tour.dto.course;

import lombok.Data;

@Data
public class TourUpdateItemRequest {
    String title;
    int idx;
    int level;
    int day;

    public TourUpdateItemRequest() {
        this.level = -1;
        this.day = -1;
    }
}
