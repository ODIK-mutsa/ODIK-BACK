package com.micutne.odik.domain.tour.dto.course;

import com.micutne.odik.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TourCourseRequest {
    String title;
    String state;
    User user;
    String like;
    String time;
}
