package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx")
    private User user;
    @Column(nullable = false)
    private Double locationLat;
    @Column(nullable = false)
    private Double locationLng;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String referenceIdGoogle;
    private String phoneNumber;
    private Float pointGoogle;
    private String type;
    @OneToMany(mappedBy = "tourItemIdx", cascade = CascadeType.ALL)
    @CreatedDate
    private List<ImageTourItem> imagesGoogle;
    @OneToMany(mappedBy = "tourItemIdx", cascade = CascadeType.ALL)
    @CreatedDate
    private List<ReviewTourItem> reviewTourItem;

    @Builder
    public TourItem(String title, User user, Double location_lat, Double location_lng, String state, String address, String reference_id_google, String phone_number, Float point_google, String type) {
        this.title = title;
        this.user = user;
        this.locationLat = location_lat;
        this.locationLng = location_lng;
        this.state = "public";
        this.address = address;
        this.referenceIdGoogle = reference_id_google;
        this.phoneNumber = phone_number;
        this.pointGoogle = point_google;
        this.type = type;

    }


    public static TourItem fromDto(TourItemRequest request) {
        TourItem tourItem = new TourItem();
        tourItem.title = request.getTitle();
        tourItem.user = request.getUser();
        tourItem.locationLng = request.getLocation_lng();
        tourItem.locationLat = request.getLocation_lat();
        tourItem.state = request.getState();
        tourItem.address = request.getAddress();
        return tourItem;
    }

    public void updateTourItem(TourItemRequest request) {
        this.title = request.getTitle();
        this.locationLat = request.getLocation_lat();
        this.locationLng = request.getLocation_lng();
        this.state = request.getState();
        this.address = request.getAddress();
    }

    public void updateState(String state) {
        this.state = state;
    }



    public void updateUser(User user) {
        this.user = user;
    }

}
