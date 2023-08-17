package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.review.ReviewItem;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User userIdx;

    private Double locationLat;
    private Double locationLng;

    private String locationLabelLv1;
    private String locationLabelLv2;
    private String locationLabelLv3;

    @OneToMany(mappedBy = "tour_item", cascade = CascadeType.ALL)
    private List<ReviewItem> reviewItem;



    @Builder
    public TourItem(String title, User userIdx, Double locationLat, Double locationLng, String locationLabelLv1, String locationLabelLv2, String locationLabelLv3) {
        this.title = title;
        this.userIdx = userIdx;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.locationLabelLv1 = locationLabelLv1;
        this.locationLabelLv2 = locationLabelLv2;
        this.locationLabelLv3 = locationLabelLv3;
    }

}
