package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import lombok.Data;

@Data
public class TourCourseResultResponse {
    String result;
    TourCourseResponse tour_course;

    public static TourCourseResultResponse fromEntity(TourCourse tourCourse, int like, String result) {
        TourCourseResultResponse response = new TourCourseResultResponse();

        response.tour_course = TourCourseResponse.fromEntity(tourCourse);
        response.tour_course.setLike(like);
        response.result = result;
        return response;
    }

    public static TourCourseResultResponse fromEntity(TourCourse tourCourse, String result) {
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.tour_course = TourCourseResponse.fromEntity(tourCourse);
        response.result = result;
        return response;
    }

    public static TourCourseResultResponse fromEntity(String result) {
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.result = result;
        return response;
    }
}
