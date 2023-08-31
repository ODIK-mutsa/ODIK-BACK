package com.micutne.odik.service;


import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.course.TourItemResponse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.TourCourseListTourItemRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourCourseService {
    private final TourCourseRepository tourCourseRepository;
    private final TourItemRepository tourItemRepository;
    private final TourCourseListTourItemRepository tourCourseItemListRepository;
    private final UserRepository userRepository;

    public TourCourseResponse readMyCourse(String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse;
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            tourCourse = createMyCourse(user);
        }
        // 장바구니 코스가 이미 있는 경우
        else {
            tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
        }
        return TourCourseResponse.fromEntity(tourCourse);
    }

    public TourCourseResponse create(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            request.setUser(user);
            request.setState("cart");
            TourCourseResponse response = TourCourseResponse.fromEntity(tourCourseRepository.save(TourCourse.fromDto(request)));
            response.setResult("OK");
            return response;
        } else {
            TourCourseResponse response = TourCourseResponse.fromEntity(tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart"));
            response.setResult("ALREADY_EXIST");
            return response;
        }

    }

    public TourCourse createMyCourse(User user) {
        TourCourseRequest makeCourse = new TourCourseRequest();
        makeCourse.setUser(user);
        makeCourse.setTitle("장바구니");
        makeCourse.setState("cart");
        return tourCourseRepository.save(TourCourse.fromDto(makeCourse));
    }

    // 수정
    @Transactional
    public TourCourseResponse updateMyCourse(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            TourCourse tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
            checkAuth(tourCourse, user);
            tourCourse.update(request);
            return TourCourseResponse.fromEntity(tourCourse);
        }
        TourCourseResponse response = new TourCourseResponse();
        response.setResult("NOT_EXIST");
        return response;
    }

    // 삭제
    public void remove(int idx, String username) {
        TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(idx);
        User user = userRepository.findByIdOrThrow(username);
        checkAuth(tourCourse, user);
        //TODO 삭제
    }


    public void checkAuth(TourCourse tourCourse, User user) {
        if (!tourCourse.getUserIdx().equals(user)) throw new AuthException(ErrorCode.NOT_YOURS);
    }


    public TourItemResponse addTourItem(TourAddItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByUserIdxAndState(user, "public")) {
            return createTourItem(request, tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx()));
        } else {
            return new TourItemResponse("COURSE_NOT_EXIST");
        }
    }

    @Transactional
    public TourItemResponse createTourItem(TourAddItemRequest request, TourCourse tourCourse) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(request.getTour_item_idx());


        if (!tourCourseItemListRepository.existsByTourCourseAndTourItem(tourCourse, tourItem)) {
            if (!tourCourseItemListRepository.existsByDayAndLevel(request.getDay(), request.getLevel())) {
                request.setTourCourse(tourCourse);
                request.setTourItem(tourItem);

                tourCourseItemListRepository.save(TourCourseListTourItem.fromDto(request));

                return new TourItemResponse("OK");
            } else return new TourItemResponse("ALREADY_EXIST");
        }
        //이미 코스에 그 아이템이 있는 경우
        else return new TourItemResponse("ALREADY_EXIST");
    }

    @Transactional
    public TourItemResponse addMyTourItem(TourAddItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse;
        // 장바구니 코스가 없는 경우
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            tourCourse = createMyCourse(user);
        }
        // 장바구니 코스가 이미 있는 경우
        else {
            tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
        }
        return createTourItem(request, tourCourse);
    }
}
