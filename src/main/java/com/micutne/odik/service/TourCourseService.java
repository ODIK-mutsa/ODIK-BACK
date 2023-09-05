package com.micutne.odik.service;


import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.dto.course.*;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.TourCourseListTourItemRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourCourseService {
    private final TourCourseRepository tourCourseRepository;
    private final TourItemRepository tourItemRepository;
    private final TourCourseListTourItemRepository tourCourseItemListRepository;
    private final UserRepository userRepository;

    public TourCourseResultResponse readMyCourse(String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse;
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            tourCourse = createMyCourse(user);
        }
        // 장바구니 코스가 이미 있는 경우
        else {
            tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
        }
        TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourse);
        response.setResult("OK");
        return response;
    }

    public TourCourseResultResponse readOne(int course) {
        if (tourCourseRepository.existsByIdxAndState(course, "public")) {
            TourCourse tourCourse = tourCourseRepository.findByIdxAndStateOrThrow(course, "public");
            TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourse);
            response.setResult("OK");
            return response;
        }
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.setResult("NOT_EXIST");
        return response;
    }

    public Page<TourCourseResponse> readAll(String title, int pageNo, int pageSize) {
        log.info("title : " + title);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        String STATE = "public";
        Page<TourCourse> entities = tourCourseRepository.findAllByStateAndTitleContaining(STATE, title, pageable);
        return entities.map(TourCourseResponse::fromEntityForList);
    }

    public TourCourseResultResponse create(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            request.setUser(user);
            request.setState("cart");
            TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourseRepository.save(TourCourse.fromDto(request)));
            response.setResult("OK");
            return response;
        } else {
            TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart"));
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
    public TourCourseResultResponse updateCourse(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            TourCourse tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
            checkAuth(tourCourse, user);
            tourCourse.update(request);
            TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourse);
            response.setResult("OK");
            return response;
        }
        TourCourseResultResponse response = new TourCourseResultResponse();
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


    public TourCourseResultResponse addTourItem(TourAddItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx());
        checkAuth(tourCourse, user);
        tourCourse.update(request);
        if (tourCourseRepository.existsByIdx(request.getTour_course_idx())) {
            List<TourCourseListTourItem> beforeItems = tourCourse.getTourCourseItemLists();

            tourCourseItemListRepository.deleteAll(beforeItems);

            List<TourUpdateItemRequest> newItems = request.getTour_items();
            newItems.forEach(item -> item.setItem(tourItemRepository.findByIdOrThrow(item.getIdx())));

            List<TourCourseListTourItem> afterItems = tourCourseItemListRepository.saveAll(newItems.stream().map(i -> TourCourseListTourItem.fromDto(i, tourCourse)).toList());

            TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx()));
            response.setResult("OK");
            return response;
        }
        TourCourseResultResponse response = TourCourseResultResponse.fromEntity(tourCourse);
        response.setResult("NOT_EXIST");
        return response;
    }


//    public TourItemResponse addTourItem(TourAddItemRequest request, String username) {
//        User user = userRepository.findByIdOrThrow(username);
//        if (tourCourseRepository.existsByIdx(request.getTour_course_idx())) {
//            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx());
//            List<TourCourseListTourItem> beforeItems = tourCourse.getTourCourseItemLists();
//            List<TourUpdateItemRequest> newItems = request.getTour_items();
//            newItems.forEach(item -> item.setItem(tourItemRepository.findByIdOrThrow(item.getIdx())));
//            List<TourItem> after_items = newItems.stream().map(TourUpdateItemRequest::getItem).toList();
//            List<TourUpdateItemRequest> processedItems = new ArrayList<>();
//
//            if (!beforeItems.isEmpty()) {
//                for (TourCourseListTourItem temp : beforeItems) {
//                    //수정
//                    if (after_items.contains(temp.getTourItem())) {
//                        TourUpdateItemRequest updateTemp = newItems.stream().filter(item -> item.getIdx() == temp.getTourItem().getIdx()).findFirst().get();
//                        processedItems.add(updateTemp);
//                        temp.update(updateTemp);
//                    }
//                    //삭제
//                    else {
//                        tourCourseItemListRepository.delete(temp);
//                        beforeItems.remove(temp);
//                    }
//                }
//            }
//
//            //tourCourseItemListRepository.saveAll(processedItems.stream().map(i -> TourCourseListTourItem.fromDto(i, tourCourse)).toList());
//            newItems.removeAll(processedItems);
//            log.info(newItems.toString());
//            tourCourseItemListRepository.saveAll(newItems.stream().map(i -> TourCourseListTourItem.fromDto(i, tourCourse)).toList());
//            return new TourItemResponse("OK");
//        } else return new TourItemResponse("NOT_EXIST");
//    }
}
