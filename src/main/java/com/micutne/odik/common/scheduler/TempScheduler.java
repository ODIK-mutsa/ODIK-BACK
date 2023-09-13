package com.micutne.odik.common.scheduler;

import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.service.ReviewTourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TempScheduler implements Job {
    private final TourCourseRepository tourCourseRepository;
    private final ReviewTourCourseService reviewTourCourseService;

    //전에 리뷰 업데이트
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("리뷰 업데이트 시작");
        List<TourCourse> courses = tourCourseRepository.findAll();
        for (TourCourse tourCourse : courses) {
            reviewTourCourseService.updateTotalRating(tourCourse);
        }
        log.info("리뷰 업데이트 완료");
    }
}
