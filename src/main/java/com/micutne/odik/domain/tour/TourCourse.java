package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tour_course")
@Getter
@Setter
public class TourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 30)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user_idx;

    private LocalDateTime date_create;
    private LocalDateTime date_modify;

    @OneToMany(mappedBy = "tourCourse")
    private List<TourCourseItemList> tourCourseItemLists = new ArrayList<>();

}

