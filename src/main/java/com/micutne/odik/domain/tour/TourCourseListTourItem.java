package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourUpdateItemRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TourCourseListTourItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private int level;
    private int day;

    @ManyToOne
    @JoinColumn
    private TourCourse tourCourse;

    @ManyToOne
    @JoinColumn
    private TourItem tourItem;


    public static TourCourseListTourItem fromDto(TourAddItemRequest request) {
        TourCourseListTourItem tourCourseItemList = new TourCourseListTourItem();
        tourCourseItemList.tourCourse = request.getTourCourse();
        tourCourseItemList.tourItem = request.getTourItem();
        tourCourseItemList.level = request.getLevel();
        tourCourseItemList.day = request.getDay();
        return tourCourseItemList;
    }

    public void update(TourUpdateItemRequest request) {
        level = request.getLevel() != -1 ? request.getLevel() : level;
        day = request.getDay() != -1 ? request.getDay() : day;
    }

}
