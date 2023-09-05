package com.micutne.odik.service;

import com.micutne.odik.repository.HistoryLikeTourCourseRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryLikeCourseService {
    private final TourCourseRepository tourCourseRepository;
    private final UserRepository userRepository;
    private final HistoryLikeTourCourseRepository historyLikeTourCourseRepository;
}
