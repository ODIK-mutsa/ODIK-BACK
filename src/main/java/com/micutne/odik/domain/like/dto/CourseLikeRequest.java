package com.micutne.odik.domain.like.dto;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseLikeRequest {
    boolean like;
    TourCourse tourCourse;
    User user;

}
