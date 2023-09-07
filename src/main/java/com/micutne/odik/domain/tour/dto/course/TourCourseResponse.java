package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TourCourseResponse {
    int idx;
    String title;
    String state;
    int countLike;
    ProfileResponse user;
    String date_join;
    List<TourCourseItemResponse> tour_items = new ArrayList<>();

    public static TourCourseResponse fromEntity(TourCourse tourCourse) {
        TourCourseResponse response = new TourCourseResponse();
        response.idx = tourCourse.getIdx();
        response.title = tourCourse.getTitle();
        response.state = tourCourse.getState();
        response.user = ProfileResponse.fromEntity(tourCourse.getUserIdx());
        response.date_join = TimeUtils.getLocalTime(tourCourse.getDateCreate());
        response.countLike = tourCourse.getCountLike();
        response.tour_items = tourCourse.getTourCourseItemLists().stream().map(TourCourseItemResponse::fromEntity).toList();
        return response;
    }
}
