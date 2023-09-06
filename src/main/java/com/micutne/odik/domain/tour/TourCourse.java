package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TourCourse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(length = 30)
    private String title;
    @Column(length = 8)
    private String state;
    @Column
    private int countLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User userIdx;


    @OneToMany(mappedBy = "tourCourse")
    private List<TourCourseListTourItem> tourCourseItemLists = new ArrayList<>();

    public static TourCourse fromDto(TourCourseRequest request) {
        TourCourse tourCourse = new TourCourse();
        tourCourse.title = request.getTitle();
        tourCourse.state = request.getState();
        tourCourse.userIdx = request.getUser();
        return tourCourse;
    }

    public void update(TourCourseRequest request) {
        title = (request.getTitle() != null) ? request.getTitle() : title;
        state = (request.getState() != null) ? request.getState() : state;
    }

    public void update(TourAddItemRequest request) {
        title = (request.getTitle() != null) ? request.getTitle() : title;
        state = (request.getState() != null) ? request.getState() : state;
    }

    public void updateLike(int i) {
        countLike += i;
    }
}