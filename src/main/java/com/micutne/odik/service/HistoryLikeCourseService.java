package com.micutne.odik.service;

import com.micutne.odik.domain.like.HistoryLikeTourCourse;
import com.micutne.odik.domain.like.dto.CourseLikeRequest;
import com.micutne.odik.domain.like.dto.LikeResponse;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.HistoryLikeTourCourseRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryLikeCourseService {
    private final TourCourseRepository tourCourseRepository;
    private final UserRepository userRepository;
    private final HistoryLikeTourCourseRepository historyLikeTourCourseRepository;

    public LikeResponse read(int courseId, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByIdx(courseId)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(courseId);

            if (!historyLikeTourCourseRepository.existsByTourCourseAndUser(tourCourse, user))
                return LikeResponse.toDto(false, "OK");

            return LikeResponse.toDto(true, "OK");
        }
        return LikeResponse.toDto("COURSE_NOT_EXIST");
    }

    @Transactional
    public LikeResponse update(int courseId, CourseLikeRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByIdx(courseId)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(courseId);

            //true : 생성
            if (request.isLike()) {
                //이미 있는 경우
                if (historyLikeTourCourseRepository.existsByTourCourseAndUser(tourCourse, user))
                    return LikeResponse.toDto(true, "OK");

                request.setUser(user);
                request.setTourCourse(tourCourse);
                historyLikeTourCourseRepository.save(HistoryLikeTourCourse.fromDto(request));
                tourCourse.updateLike(1);
                return LikeResponse.toDto(true, "OK");
            }
            //false : 제거
            //이미 없는 경우
            if (!historyLikeTourCourseRepository.existsByTourCourseAndUser(tourCourse, user))
                return LikeResponse.toDto(false, "OK");

            HistoryLikeTourCourse entity = historyLikeTourCourseRepository.findByTourCourseAndUserOrThrow(tourCourse, user);
            historyLikeTourCourseRepository.delete(entity);
            tourCourse.updateLike(-1);

            return LikeResponse.toDto(false, "OK");
        }
        return LikeResponse.toDto("COURSE_NOT_EXIST");
    }
}
