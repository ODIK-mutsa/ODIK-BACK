package com.micutne.odik.domain.images;

import com.micutne.odik.domain.tour.TourItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private TourItem tourItemIdx;
    @Lob
    @Column(columnDefinition = "TEXT", name = "url")
    private String imagesGoogle;

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    @Builder
    public ImageTourItem(TourItem tour_item_idx, String url) {
        this.tourItemIdx = tour_item_idx;
        this.imagesGoogle = url;
    }
}


