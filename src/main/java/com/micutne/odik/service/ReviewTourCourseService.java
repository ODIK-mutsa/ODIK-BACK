package com.micutne.odik.service;

import com.micutne.odik.domain.review.ReviewCourse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseResultResponse;
import com.micutne.odik.domain.review.dto.course.ReviewTourCourseRequest;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.ReviewTourCourseRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTourCourseService {
    private final ReviewTourCourseRepository reviewCourseRepository;
    private final TourCourseRepository tourCourseRepository;
    private final UserRepository userRepository;

    public ReviewCourseResultResponse create(ReviewTourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        int courseId = request.getTour_course_idx();
        if (tourCourseRepository.existsByIdx(courseId)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(courseId);

            request.setUser(user);
            request.setTourCourse(tourCourse);

            ReviewCourse reviewCourse = reviewCourseRepository.save(ReviewCourse.fromDto(request));
            return ReviewCourseResultResponse.fromEntity(reviewCourse, "OK");
        }
        return ReviewCourseResultResponse.fromEntity("COURSE_NOT_EXIST");
    }

    @Transactional
    public ReviewCourseResultResponse update(ReviewTourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (reviewCourseRepository.existsByIdx(request.getReview_course_idx())) {
            ReviewCourse reviewCourse = reviewCourseRepository.findByIdxOrThrow(request.getReview_course_idx());

            TourCourse tourCourse = reviewCourse.getTourCourse();

            if (!tourCourse.getState().equals("public"))
                return ReviewCourseResultResponse.fromEntity("COURSE_NOT_PUBLIC");

            if (!checkAuth(reviewCourse, user)) return ReviewCourseResultResponse.fromEntity("AUTH_FAIL");

            reviewCourse.update(request);
            return ReviewCourseResultResponse.fromEntity(reviewCourse, "OK");
        }
        return ReviewCourseResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }

    public boolean checkAuth(ReviewCourse reviewCourse, User user) {
        return reviewCourse.getUser().equals(user);
    }
}
