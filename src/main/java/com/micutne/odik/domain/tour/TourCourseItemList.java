package com.micutne.odik.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class TourCourseItemList {
    @Id @GeneratedValue
    private Long idx;
    private String title;

    @ManyToOne
    @JoinColumn(name = "tour_course_idx")
    private TourCourse tour_Course_idx;

    @ManyToOne
    @JoinColumn(name  = "tour_item_idx")
    private TourItem tour_item_idx;
    private String order;

    private LocalDateTime date_create;


    @ManyToOne
    @JoinColumn(name = "tour_course_idx")
    private TourCourse tourCourse;

}
