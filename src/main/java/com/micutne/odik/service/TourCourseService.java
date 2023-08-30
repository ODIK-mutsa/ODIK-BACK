package com.micutne.odik.service;


import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
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


    public TourCourseResponse create(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        request.setUser(user);
        TourCourse tourCourse = TourCourse.fromDto(request);
        return TourCourseResponse.fromEntity(tourCourseRepository.save(tourCourse));
    }

    // 수정
    public TourCourseResponse update(int idx, TourCourseRequest request, String username) {
        TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(idx);
        User user = userRepository.findByIdOrThrow(username);
        checkAuth(tourCourse, user);
        tourCourse.update(request);
        return TourCourseResponse.fromEntity(tourCourse);
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

//    public TourCourseResponse addMyTourItem(TourAddItemRequest request, String username) {
//        User user = userRepository.findByIdOrThrow(username);
//        if(tourCourseRepository.existsByUserIdxAndState(request.))
//    }

    @Transactional
    public TourCourseResponse addMyTourItem(TourAddItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse;
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            log.info("해당 코스가 없음");
            TourCourseRequest makeCourse = new TourCourseRequest();
            makeCourse.setUser(user);
            makeCourse.setTitle("장바구니");
            makeCourse.setState("cart");
            tourCourse = tourCourseRepository.save(TourCourse.fromDto(makeCourse));
        } else {
            log.info("해당 코스가 있음");
            tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
        }
        request.setTourCourse(tourCourse);
        request.setTourItem(tourItemRepository.findByIdOrThrow(request.getTour_item_idx()));
        TourCourseListTourItem tourCourseItemList = tourCourseItemListRepository.save(TourCourseListTourItem.fromDto(request));
        TourCourseResponse response
                = TourCourseResponse.fromEntity(tourCourseRepository.findByIdxOrThrow(tourCourseItemList.getTourCourse().getIdx()));
        response.setResult("OK");
        return response;
    }
}
