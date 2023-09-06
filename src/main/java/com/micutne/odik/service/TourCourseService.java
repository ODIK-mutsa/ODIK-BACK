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
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        return TourCourseResultResponse.fromEntity(tourCourse, "OK");
    }

    public TourCourseResultResponse readOne(int course) {
        if (tourCourseRepository.existsByIdxAndState(course, "public")) {
            TourCourse tourCourse = tourCourseRepository.findByIdxAndStateOrThrow(course, "public");
            return TourCourseResultResponse.fromEntity(tourCourse, "OK");
        }
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.setResult("NOT_EXIST");
        return response;
    }

    public TourCourseResultListResponse searchAll(String title, String orderBy, int pageNo, int pageSize) {
        title = URLDecoder.decode(title, StandardCharsets.UTF_8);
        log.info(title);
        String[] keywords = title.split(" ");
        Specification<TourCourse> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 검색어가 있으면 키워드에 따라 title을 like 검색
            if (keywords.length > 0) {
                Predicate[] keywordPredicates = new Predicate[keywords.length];
                for (int i = 0; i < keywords.length; i++) {
                    keywordPredicates[i] = criteriaBuilder.like(root.get("title"), "%" + keywords[i] + "%");
                }
                predicates.add(criteriaBuilder.or(keywordPredicates));
            }

            predicates.add(criteriaBuilder.equal(root.get("state"), "public"));
            // 추가적인 조건을 여기에 추가할 수 있음

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable;
        String[] sortData = findField(orderBy);
        // 정렬 설정
        if (sortData[1].equals("desc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortData[0]));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortData[0]));
        }
        Page<TourCourse> tourCourses = tourCourseRepository.findAll(spec, pageable);

        return TourCourseResultListResponse.fromEntity(tourCourses.map(TourCourseResponse::fromEntityForList), "OK");
    }

    private String[] findField(String orderBy) {
        switch (orderBy) {
            case "recent" -> {
                return new String[]{"dateCreate", "desc"};
            }
            case "like" -> {
                return new String[]{"countLike", "asc"};
            }
            default -> {
                return new String[]{"dateCreate", "desc"};
            }
        }
    }

    public TourCourseResultListResponse readMyCourses(String username, int pageNo, int pageSize) {
        User user = userRepository.findByIdOrThrow(username);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TourCourse> entities = tourCourseRepository.findAllByUserCourse(user, pageable);
        return TourCourseResultListResponse.fromEntity(entities.map(TourCourseResponse::fromEntityForList), "OK");
    }

    public TourCourseResultResponse create(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (!tourCourseRepository.existsByUserIdxAndState(user, "cart")) {
            request.setUser(user);
            request.setState("cart");

            return TourCourseResultResponse.fromEntity(tourCourseRepository.save(TourCourse.fromDto(request)), "OK");
        } else {
            TourCourse tourCourse = tourCourseRepository.findByUserIdxAndStateOrThrow(user, "cart");
            return TourCourseResultResponse.fromEntity(tourCourse, "ALREADY_EXIST");
        }

    }

    public TourCourse createMyCourse(User user) {
        TourCourseRequest makeCourse = new TourCourseRequest();
        makeCourse.setUser(user);
        makeCourse.setTitle("장바구니");
        makeCourse.setState("cart");
        return tourCourseRepository.save(TourCourse.fromDto(makeCourse));
    }


    public void checkAuth(TourCourse tourCourse, User user) {
        if (!tourCourse.getUserIdx().equals(user)) throw new AuthException(ErrorCode.NOT_YOURS);
    }


    public TourCourseResultResponse updateAll(TourAddItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx());
        checkAuth(tourCourse, user);
        tourCourse.update(request);
        if (tourCourseRepository.existsByIdx(request.getTour_course_idx())) {
            List<TourCourseListTourItem> beforeItems = tourCourse.getTourCourseItemLists();

            tourCourseItemListRepository.deleteAll(beforeItems);

            List<TourUpdateItemRequest> newItems = request.getTour_items();
            newItems.forEach(item -> item.setItem(tourItemRepository.findByIdOrThrow(item.getIdx())));

            tourCourseItemListRepository.saveAll(newItems.stream().map(i -> TourCourseListTourItem.fromDto(i, tourCourse)).toList());

            return TourCourseResultResponse
                    .fromEntity(tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx()), "OK");
        }
        return TourCourseResultResponse.fromEntity("NOT_EXIST");
    }


}
