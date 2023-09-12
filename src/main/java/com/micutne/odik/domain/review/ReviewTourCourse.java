package com.micutne.odik.domain.review;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.images.ImageReviewTourCourse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseRequest;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ReviewTourCourse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(length = 4, nullable = false)
    private float rating;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TourCourse tourCourse;

    @OneToMany(mappedBy = "reviewTourCourse", cascade = CascadeType.ALL)
    private List<ImageReviewTourCourse> reviewImage;


    public static ReviewTourCourse fromDto(ReviewCourseRequest request) {
        ReviewTourCourse reviewCourse = new ReviewTourCourse();
        reviewCourse.rating = request.getRating();
        reviewCourse.content = request.getContent();
        reviewCourse.user = request.getUser();
        reviewCourse.tourCourse = request.getTourCourse();
        return reviewCourse;
    }

    public void update(ReviewCourseRequest request) {
        if (request.getRating() != -1) rating = request.getRating();
        if (request.getContent() != null) content = request.getContent();
    }
}
