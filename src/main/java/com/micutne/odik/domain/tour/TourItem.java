package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.review.ReviewItem;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.user.User;
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
    private Long idx;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx")
    @JsonIgnore
    private User user;
    @NotNull
    private Double locationLat;
    @NotNull
    private Double locationLng;
    @NotNull
    private String state;
    @NotNull
    private String address;
    @ColumnDefault("unidentified")
    private String referenceIdGoogle;

    private String phoneNumber;

    private Float pointGoogle;

    //@OneToMany(mappedBy = "tourItem", cascade = CascadeType.ALL)
    //@CreatedDate
    //private List<ImageTourItem> imagesGoogle;



//    @ElementCollection
//    @CollectionTable(name = "image_tour_item")
//    private List<String> url;


    //@OneToMany(mappedBy = "tour_item", cascade = CascadeType.ALL)
    //private List<ReviewItem> reviewItem;



    @Builder
    public TourItem(String title, User user, Double location_lat, Double location_lng, String state, String address, String reference_id_google, String phone_number, Float point_google ) {
        this.title = title;
        this.user = user;
        this.locationLat = location_lat;
        this.locationLng = location_lng;
        this.state = state;
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

    public void findState() {

        if (address != null && address.contains("제주특별자치도")) {
            state = "제주특별자치도";
        }
        if (address != null && address.contains("서울특별시")) {
            state = "서울특별시";
        }
        if (address != null && address.contains("부산광역시")) {
            state = "부산광역시";
        }
        if (address != null && address.contains("대구광역시")) {
            state = "대구광역시";
        }
        if (address != null && address.contains("인천광역시")) {
            state = "인천광역시";
        }
        if (address != null && address.contains("광주광역시")) {
            state = "광주광역시";
        }
        if (address != null && address.contains("대전광역시")) {
            state = "대전광역시";
        }
        if (address != null && address.contains("울산광역시")) {
            state = "울산광역시";
        }
        if (address != null && address.contains("세종특별자치시")) {
            state = "세종특별자치시";
        }
        if (address != null && address.contains("경기도")) {
            state = "경기도";
        }
        if (address != null && address.contains("강원특별자치도")) {
            state = "강원특별자치도";
        }
        if (address != null && address.contains("충청북도")) {
            state = "충청북도";
        }
        if (address != null && address.contains("충청남도")) {
            state = "충청남도";
        }
        if (address != null && address.contains("전라북도")) {
            state = "전라북도";
        }
        if (address != null && address.contains("전라남도")) {
            state = "전라남도";
        }
        if (address != null && address.contains("경상북도")) {
            state = "경상북도";
        }
        if (address != null && address.contains("경상남도")) {
            state = "경상남도";
        }
        if (address == null) {
            state = "unknown";
        }
    }

    public void updateUser(User user) {
        this.user = user;
    }

}
