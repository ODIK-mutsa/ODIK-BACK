package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.review.ReviewItem;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
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
    //@JsonIgnore
    private User user;
    @Column(nullable = false)
    private Double locationLat;
    @Column(nullable = false)
    private Double locationLng;
    // 상품의 상태: 카트에 담겨있는 상품인지, 게시판에 모두공개로 올라가있는 상태인지, 코스에 포함되어 있는지, 계획에 포함되어있는지 등.
    // cart, public, course, plan
    //@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'public'")
    @Column(nullable = false)
    //@ColumnDefault("public")
    private String state;
    @Column(nullable = false)
    private String address;
    @ColumnDefault("unidentified")
    private String referenceIdGoogle;

    private String phoneNumber;

    private Float pointGoogle;

    @OneToMany(mappedBy = "tourItemIdx", cascade = CascadeType.ALL)
    @CreatedDate
    private List<ImageTourItem> imagesGoogle;


    @OneToMany(mappedBy = "tourItemIdx", cascade = CascadeType.ALL)
    private List<ReviewItem> reviewItem;



    @Builder
    public TourItem(String title, User user, Double location_lat, Double location_lng, String state, String address, String reference_id_google, String phone_number, Float point_google ) {
        this.title = title;
        this.user = user;
        this.locationLat = location_lat;
        this.locationLng = location_lng;
        // 기본 값 public으로 설정 후, 서비스에 따라 updateState를 통해 상태 변경
        this.state = "public";
        this.address = address;
        this.referenceIdGoogle = reference_id_google;
        this.phoneNumber = phone_number;
        this.pointGoogle = point_google;
       // this.imagesGoogle = images_google;

    }


    public static TourItem fromDto(TourItemRequest request) {
        TourItem tourItem = new TourItem();
        tourItem.title = request.getTitle();
        tourItem.user = request.getUser();
        //tourItem.user = UserResponse.fromEntity(tourItem.getUser());
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
