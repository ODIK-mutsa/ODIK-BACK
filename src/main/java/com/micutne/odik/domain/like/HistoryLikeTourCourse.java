package com.micutne.odik.domain.like;

import com.micutne.odik.domain.like.dto.CourseLikeRequest;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class HistoryLikeTourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn
    private TourCourse tourCourse;

    @ManyToOne
    @JoinColumn
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private Instant dateCreate;

    public static HistoryLikeTourCourse fromDto(CourseLikeRequest request) {
        HistoryLikeTourCourse historyLikeTourCourse = new HistoryLikeTourCourse();
        historyLikeTourCourse.tourCourse = request.getTourCourse();
        historyLikeTourCourse.user = request.getUser();
        return historyLikeTourCourse;
    }
}
