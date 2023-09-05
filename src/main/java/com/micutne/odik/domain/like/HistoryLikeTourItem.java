package com.micutne.odik.domain.like;

import com.micutne.odik.domain.like.dto.ItemLikeRequest;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class HistoryLikeTourItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn
    private TourItem tourItem;

    @ManyToOne
    @JoinColumn
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    public static HistoryLikeTourItem fromDto(ItemLikeRequest request) {
        HistoryLikeTourItem historyLikeTourItem = new HistoryLikeTourItem();
        historyLikeTourItem.tourItem = request.getTourItem();
        historyLikeTourItem.user = request.getUser();
        return historyLikeTourItem;
    }
}
