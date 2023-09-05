package com.micutne.odik.domain.review;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewCourse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(length = 4, nullable = false)
    private int rating;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TourCourse tourCourse;

    public static ReviewCourse fromDto(int rating, String content, User user, TourCourse tourCourse) {
        ReviewCourse reviewCourse = new ReviewCourse();
        reviewCourse.rating = rating;
        reviewCourse.content = content;
        reviewCourse.user = user;
        reviewCourse.tourCourse = tourCourse;
        return reviewCourse;
    }
}
