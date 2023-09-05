package com.micutne.odik.service;

import com.micutne.odik.repository.ReviewTourCourseRepository;
import com.micutne.odik.repository.TourCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTourCourseService {
    private final ReviewTourCourseRepository reviewTourCourseRepository;
    private final TourCourseRepository tourCourseRepository;


}
