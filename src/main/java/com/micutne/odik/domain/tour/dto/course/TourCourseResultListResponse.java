package com.micutne.odik.domain.tour.dto.course;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class TourCourseResultListResponse {
    String result;
    Page<TourCourseResponse> tour_courses;

    public static TourCourseResultListResponse fromEntity(Page<TourCourseResponse> tour_courses, String result) {
        TourCourseResultListResponse response = new TourCourseResultListResponse();
        response.result = result;
        response.tour_courses = tour_courses;
        return response;
    }

    public static TourCourseResultListResponse fromEntity(String result) {
        TourCourseResultListResponse response = new TourCourseResultListResponse();
        response.result = result;
        return response;
    }
}
