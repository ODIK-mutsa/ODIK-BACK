package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.tour.dto.course.TourUpdateItemRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TourCourseListTourItem {
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

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;


    public static TourCourseListTourItem fromDto(TourUpdateItemRequest request, TourCourse tourCourse) {
        TourCourseListTourItem response = new TourCourseListTourItem();
        response.level = request.getLevel();
        response.day = request.getDay();
        response.tourItem = request.getItem();
        response.tourCourse = tourCourse;
        return response;
    }

    public void update(TourUpdateItemRequest request) {
        level = request.getLevel() != -1 ? request.getLevel() : level;
        day = request.getDay() != -1 ? request.getDay() : day;
    }

}
