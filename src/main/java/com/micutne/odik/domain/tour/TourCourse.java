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
public class TourCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 30)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User userIdx;

    private LocalDateTime dateCreate;
    private LocalDateTime dateModify;

    @OneToMany(mappedBy = "tourCourse")
    private List<TourCourseItemList> tourCourseItemLists = new ArrayList<>();

}

