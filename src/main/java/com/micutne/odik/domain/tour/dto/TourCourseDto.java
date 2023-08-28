package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourCourseDto {
    private Long idx;
    private String title;
    private User user_idx;
    private LocalDateTime date_create;
    private LocalDateTime date_modify;


    public static TourCourseDto fromEntity(TourCourse tourItem) {
        TourCourseDto dto = new TourCourseDto();
        dto.setTitle(tourItem.getTitle());
        dto.setUser_idx(tourItem.getUserIdx());
        dto.setDate_create(tourItem.getDateCreate());
        dto.setDate_modify(tourItem.getDateModify());
        return dto;
    }
}
