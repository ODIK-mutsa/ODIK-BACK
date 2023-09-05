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
<<<<<<< HEAD
    long like;
=======
>>>>>>> 86a81eb1f8412865facd1bd492726662d7c2f619

    ProfileResponse user;
    List<TourCourseItemResponse> tour_items = new ArrayList<>();

    public static TourCourseResponse fromEntity(TourCourse tourCourse) {
        TourCourseResponse response = new TourCourseResponse();
        response.idx = tourCourse.getIdx();
        response.title = tourCourse.getTitle();
        response.state = tourCourse.getState();
<<<<<<< HEAD
        response.like = tourCourse.getLike();
=======
>>>>>>> 86a81eb1f8412865facd1bd492726662d7c2f619
        response.user = ProfileResponse.fromEntity(tourCourse.getUserIdx());
        response.tour_items = tourCourse.getTourCourseItemLists().stream().map(TourCourseItemResponse::fromEntity).toList();
        return response;
    }

    public static TourCourseResponse fromEntityForList(TourCourse tourCourse) {
        TourCourseResponse response = new TourCourseResponse();
        response.idx = tourCourse.getIdx();
        response.title = tourCourse.getTitle();
        response.state = tourCourse.getState();
<<<<<<< HEAD
        response.like = tourCourse.getLike();
=======
>>>>>>> 86a81eb1f8412865facd1bd492726662d7c2f619
        response.user = ProfileResponse.fromEntity(tourCourse.getUserIdx());
        return response;
    }
}
