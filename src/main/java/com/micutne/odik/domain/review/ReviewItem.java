package com.micutne.odik.domain.review;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx")
    private User userIdx;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_item_idx")
    private TourItem tourItemIdx;

    @Builder
    public ReviewItem(int rating, String content, User userIdx, TourItem tourItemIdx) {
        this.rating = rating;
        this.content = content;
        this.userIdx = userIdx;
        this.tourItemIdx = tourItemIdx;
    }


}
