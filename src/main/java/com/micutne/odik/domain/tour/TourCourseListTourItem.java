package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
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
    private String title;

    @ManyToOne
    @JoinColumn
    private TourCourse tourCourse;

    @ManyToOne
    @JoinColumn
    private TourItem tourItem;
    @Column
    private int level;


    public static TourCourseListTourItem fromDto(TourAddItemRequest request) {
        TourCourseListTourItem tourCourseItemList = new TourCourseListTourItem();
        tourCourseItemList.title = request.getTitle();
        tourCourseItemList.tourCourse = request.getTourCourse();
        tourCourseItemList.tourItem = request.getTourItem();
        tourCourseItemList.level = request.getLevel();
        return tourCourseItemList;
    }

}
