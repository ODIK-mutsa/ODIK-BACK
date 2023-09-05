package com.micutne.odik.service;

import com.micutne.odik.domain.review.ReviewTourCourse;
import com.micutne.odik.domain.review.dto.course.ReviewCoursePageResultResponse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseRequest;
import com.micutne.odik.domain.review.dto.course.ReviewCourseResponse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseResultResponse;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.ReviewTourCourseRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTourCourseService {
    private final ReviewTourCourseRepository reviewCourseRepository;
    private final TourCourseRepository tourCourseRepository;
    private final UserRepository userRepository;


    public ReviewCoursePageResultResponse readCourse(int courseId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (tourCourseRepository.existsByIdx(courseId)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(courseId);

            Page<ReviewTourCourse> entities = reviewCourseRepository.findAllByTourCourse(tourCourse, pageable);
            Page<ReviewCourseResponse> dtos = entities.map(ReviewCourseResponse::fromEntity);
            return ReviewCoursePageResultResponse.fromPage("OK", dtos);
        }
        return ReviewCoursePageResultResponse.fromPage("COURSE_NOT_EXIST");


    }

    public ReviewCourseResultResponse readReview(int reviewId) {
        if (reviewCourseRepository.existsByIdx(reviewId)) {
            return ReviewCourseResultResponse.fromEntity(reviewCourseRepository.findByIdxOrThrow(reviewId), "OK");
        }
        return ReviewCourseResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }

    public ReviewCourseResultResponse create(ReviewCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        int courseId = request.getTour_course_idx();
        if (tourCourseRepository.existsByIdx(courseId)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(courseId);

            if (!tourCourse.getState().equals("public"))
                return ReviewCourseResultResponse.fromEntity("STATE_NOT_PUBLIC");

            if (!reviewCourseRepository.existsByTourCourseAndUser(tourCourse, user)) {
                request.setUser(user);
                request.setTourCourse(tourCourse);

                ReviewTourCourse reviewCourse = reviewCourseRepository.save(ReviewTourCourse.fromDto(request));
                return ReviewCourseResultResponse.fromEntity(reviewCourse, "OK");
            }
            return ReviewCourseResultResponse.fromEntity("ALREADY_CREATE");
        }
        return ReviewCourseResultResponse.fromEntity("COURSE_NOT_EXIST");
    }

    @Transactional
    public ReviewCourseResultResponse update(ReviewCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);

        if (reviewCourseRepository.existsByIdx(request.getReview_course_idx())) {
            ReviewTourCourse reviewCourse = reviewCourseRepository.findByIdxOrThrow(request.getReview_course_idx());

            TourCourse tourCourse = reviewCourse.getTourCourse();

            if (!tourCourse.getState().equals("public"))
                return ReviewCourseResultResponse.fromEntity("STATE_NOT_PUBLIC");

            if (!checkAuth(reviewCourse, user)) return ReviewCourseResultResponse.fromEntity("AUTH_FAIL");

            reviewCourse.update(request);
            return ReviewCourseResultResponse.fromEntity(reviewCourse, "OK");
        }
        return ReviewCourseResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }

    public boolean checkAuth(ReviewTourCourse reviewCourse, User user) {
        return reviewCourse.getUser().equals(user);
    }

    public ReviewCourseResultResponse delete(ReviewCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);

        if (reviewCourseRepository.existsByIdx(request.getReview_course_idx())) {
            ReviewTourCourse reviewCourse = reviewCourseRepository.findByIdxOrThrow(request.getReview_course_idx());

            TourCourse tourCourse = reviewCourse.getTourCourse();

            if (!tourCourse.getState().equals("public"))
                return ReviewCourseResultResponse.fromEntity("STATE_NOT_PUBLIC");

            if (!checkAuth(reviewCourse, user)) return ReviewCourseResultResponse.fromEntity("AUTH_FAIL");

            reviewCourseRepository.delete(reviewCourse);
            return ReviewCourseResultResponse.fromEntity("OK");
        }
        return ReviewCourseResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }


}