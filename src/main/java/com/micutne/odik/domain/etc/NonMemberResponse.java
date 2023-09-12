package com.micutne.odik.domain.etc;

import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.item.TourItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class NonMemberResponse {
    List<TourItemResponse> tour_items;
    List<TourCourseResponse> tour_courses;
    List<TourCourseResponse> latest_courses;

    public static NonMemberResponse toDto(List<TourItemResponse> tour_items, List<TourCourseResponse> tour_courses, List<TourCourseResponse> latest_courses) {
        NonMemberResponse response = new NonMemberResponse();
        response.tour_items = tour_items;
        response.tour_courses = tour_courses;
        response.latest_courses = latest_courses;
        return response;
    }
}
