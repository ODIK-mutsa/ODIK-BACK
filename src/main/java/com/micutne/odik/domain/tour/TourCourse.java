package com.micutne.odik.domain.tour;

import com.micutne.odik.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
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
    private User user_idx;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date_create;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date_modify;

    @OneToMany(mappedBy = "tour_course", fetch = FetchType.LAZY)
    private List<TourCourseItemList>  list = new ArrayList<>();

    public TourCourse() {
        this.idx = idx;
        this.title = title;
        this.user_idx = user_idx;
        this.date_create = date_create;
        this.date_modify = date_modify;
        this.list = list;
    }
}
