package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import lombok.Data;

@Data
public class TourCourseResultResponse {
    String result;
    TourCourseResponse tour_course;

    public static TourCourseResultResponse fromEntity(TourCourse tourCourse) {
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.tour_course = TourCourseResponse.fromEntity(tourCourse);
        return response;
    }
}
