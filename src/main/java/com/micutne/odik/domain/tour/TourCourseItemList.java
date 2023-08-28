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
    private TourCourse tourCourseIdx;

    @ManyToOne
    @JoinColumn(name  = "tour_item_idx")
    private TourItem tourItemIdx;
    private String order;

    private LocalDateTime dateCreate;


    @ManyToOne
    @JoinColumn(name = "tour_course_idx")
    private TourCourse tourCourse;

}
