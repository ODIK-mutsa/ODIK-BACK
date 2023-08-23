package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.review.ReviewItem;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
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
public class TourItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    private Double locationLat;
    private Double locationLng;

    private String locationLabelLv1;
    private String locationLabelLv2;
    private String locationLabelLv3;

    //@OneToMany(mappedBy = "tour_item", cascade = CascadeType.ALL)
    //private List<ReviewItem> reviewItem;



    @Builder
    public TourItem(String title, User user, Double locationLat, Double locationLng, String locationLabelLv1, String locationLabelLv2, String locationLabelLv3) {
        this.title = title;
        this.user = user;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.locationLabelLv1 = locationLabelLv1;
        this.locationLabelLv2 = locationLabelLv2;
        this.locationLabelLv3 = locationLabelLv3;
    }

    public void updateTourItem(TourItemRequest request) {
        this.title = request.getTitle();
        this.locationLat = request.getLocationLat();
        this.locationLng = request.getLocationLng();
        this.locationLabelLv1 = request.getLocationLabelLv1();
        this.locationLabelLv2 = request.getLocationLabelLv2();
        this.locationLabelLv3 = request.getLocationLabelLv3();
    }

    public void updateUser(User user) {
        this.user = user;
    }

}
