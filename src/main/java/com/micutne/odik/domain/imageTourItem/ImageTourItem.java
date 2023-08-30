package com.micutne.odik.domain.imageTourItem;

import com.micutne.odik.domain.BaseEntity;
import com.micutne.odik.domain.tour.TourItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ImageTourItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_item_idx")
    @JsonIgnore
    private TourItem tourItemIdx;
    @Column(name = "url")
    private String imagesGoogle;

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    @Builder
    public ImageTourItem(TourItem tour_item_idx, String url) {
        this.tourItemIdx = tour_item_idx;
        //this.imagesGoogle = images_google;
        this.imagesGoogle = url;
    }

   // public ImageTourItem(String url) {
   //     this.url = url;
   // }

}


