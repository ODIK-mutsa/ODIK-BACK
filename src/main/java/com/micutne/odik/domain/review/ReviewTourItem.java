package com.micutne.odik.domain.review;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.images.ImageReviewTourItem;
import com.micutne.odik.domain.review.dto.ReviewTourItemRequest;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ReviewTourItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(length = 4, nullable = false)
    private float rating;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_item_idx")
    private TourItem tourItem;

    @OneToMany(mappedBy = "reviewTourItem", cascade = CascadeType.ALL)
    private List<ImageReviewTourItem> reviewImage;

    public static ReviewTourItem fromDto(ReviewTourItemRequest request) {
        ReviewTourItem reviewTourItem = new ReviewTourItem();
        reviewTourItem.rating = request.getRating();
        reviewTourItem.content = request.getContent();
        reviewTourItem.user = request.getUser();
        reviewTourItem.tourItem = request.getTourItem();
        return reviewTourItem;
    }

    public void update(ReviewTourItemRequest request) {
        if (request.getRating() != -1) rating = request.getRating();
        if (request.getContent() != null) content = request.getContent();
    }


    public void setReviewImage(List<ImageReviewTourItem> urls) {
        reviewImage = urls;
    }
}
