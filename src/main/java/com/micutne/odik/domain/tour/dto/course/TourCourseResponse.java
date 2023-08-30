package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TourCourseResponse {
    int idx;
    String title;
    String state;
    ProfileResponse user;
    String result;
    List<TourCourseItemResponse> tour_course_item_lists = new ArrayList<>();

    public static TourCourseResponse fromEntity(TourCourse tourCourse) {
        TourCourseResponse response = new TourCourseResponse();
        response.idx = tourCourse.getIdx();
        response.title = tourCourse.getTitle();
        response.state = tourCourse.getState();
        response.user = ProfileResponse.fromEntity(tourCourse.getUserIdx());
        response.tour_course_item_lists = tourCourse.getTourCourseItemLists().stream().map(TourCourseItemResponse::fromEntity).toList();
        return response;
    }
}
