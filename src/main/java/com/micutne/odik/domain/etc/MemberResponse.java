package com.micutne.odik.domain.etc;

import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.item.TourItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class MemberResponse {
    List<TourItemResponse> tour_items;
    List<TourCourseResponse> tour_courses;
    List<TourCourseResponse> like_courses;

    public static MemberResponse toDto(List<TourItemResponse> tour_items, List<TourCourseResponse> tour_courses, List<TourCourseResponse> like_courses) {
        MemberResponse response = new MemberResponse();
        response.tour_items = tour_items;
        response.tour_courses = tour_courses;
        response.like_courses = like_courses;
        return response;
    }
}
