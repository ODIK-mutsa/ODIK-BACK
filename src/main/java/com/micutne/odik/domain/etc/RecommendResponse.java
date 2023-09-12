package com.micutne.odik.domain.etc;

import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.item.TourItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class RecommendResponse {
    MemberResponse recommend;
    NonMemberResponse like;
    List<String> keyword;

    public void setRecommend(List<TourItemResponse> tour_items, List<TourCourseResponse> tour_courses, List<TourCourseResponse> like_courses) {
        recommend = MemberResponse.toDto(tour_items, tour_courses, like_courses);
    }

    public void setLike(List<TourItemResponse> tour_items, List<TourCourseResponse> tour_courses, List<TourCourseResponse> latest_courses) {
        like = NonMemberResponse.toDto(tour_items, tour_courses, latest_courses);
    }
}
