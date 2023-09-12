package com.micutne.odik.domain.images;

import com.micutne.odik.domain.review.ReviewTourItem;
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
public class ImageReviewTourItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tour_item")
    private ReviewTourItem reviewTourItem;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    public static ImageReviewTourItem toEntity(ReviewTourItem reviewTourItem, String url) {
        ImageReviewTourItem entity = new ImageReviewTourItem();
        entity.reviewTourItem = reviewTourItem;
        entity.url = url;
        return entity;
    }
}
