package com.micutne.odik.domain.images;

import com.micutne.odik.domain.review.ReviewTourCourse;
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
public class ImageReviewTourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tour_course")
    private ReviewTourCourse reviewTourCourse;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;
    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    public static ImageReviewTourCourse toEntity(ReviewTourCourse reviewTourCourse, String url) {
        ImageReviewTourCourse entity = new ImageReviewTourCourse();
        entity.reviewTourCourse = reviewTourCourse;
        entity.url = url;
        return entity;
    }
}
