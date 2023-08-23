package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourCourseDto {
    private Long idx;
    private String title;
    private User user_idx;
    private Date date_create;
    private Date date_modify;


    public static TourCourseDto fromEntity(TourCourse tourItem) {
        TourCourseDto dto = new TourCourseDto();
        dto.setTitle(tourItem.getTitle());
        dto.setUser_idx(tourItem.getUser_idx());
        dto.setDate_create(tourItem.getDate_create());
        dto.setDate_modify(tourItem.getDate_modify());
        return dto;
    }
}
