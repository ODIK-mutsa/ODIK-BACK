package com.micutne.odik.service;


import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourCourseListTourItem;
import com.micutne.odik.domain.tour.dto.course.*;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.TourCourseListTourItemRepository;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 내 장바구니 불러오기 / 없으면 생성하기
     */
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

    /**
     * 단일 코스 출력
     */
    public TourCourseResultResponse readOne(int course) {
        if (tourCourseRepository.existsByIdxAndState(course, "public")) {
            TourCourse tourCourse = tourCourseRepository.findByIdxAndStateOrThrow(course, "public");
            return TourCourseResultResponse.fromEntity(tourCourse, "OK");
        }
        TourCourseResultResponse response = new TourCourseResultResponse();
        response.setResult("NOT_EXIST");
        return response;
    }

    /**
     * 코스 검색
     */
    public TourCourseResultListResponse searchAll(String[] keywords, String orderBy, int pageNo, int pageSize) {
        Specification<TourCourse> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // TourCourse와 TourCourseListTourItem을 JOIN
            Join<TourCourse, TourCourseListTourItem> tourCourseListTourItemJoin = root.join("tourCourseItemLists", JoinType.INNER);
            // 검색어가 있으면 키워드에 따라 title을 like 검색
            if (keywords.length > 0) {
                Predicate[] keywordPredicates = new Predicate[keywords.length];
                for (int i = 0; i < keywords.length; i++) {
                    keywordPredicates[i] = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("title"), "%" + keywords[i] + "%"),
                            criteriaBuilder.like(tourCourseListTourItemJoin.get("tourItem").get("title"), "%" + keywords[i] + "%")
                    );
                }
                predicates.add(criteriaBuilder.or(keywordPredicates));
            }

            predicates.add(criteriaBuilder.equal(root.get("state"), "public"));

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
        return TourCourseResultListResponse.fromEntity(tourCourses.map(TourCourseResponse::fromEntity), "OK");
    }

    /**
     * 코스 order 에 맞춰 값 출력 [field, order by]
     */
    private String[] findField(String orderBy) {
        switch (orderBy) {
            case "recent" -> {
                return new String[]{"dateCreate", "desc"};
            }
            case "like" -> {
                return new String[]{"countLike", "desc"};
            }
            default -> {
                return new String[]{"countLike", "desc"};
            }
        }
    }

    /**
     * 내 장바구니 불러오기
     */
    public TourCourseResultListResponse readMyCourses(String username, int pageNo, int pageSize) {
        User user = userRepository.findByIdOrThrow(username);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TourCourse> entities = tourCourseRepository.findAllByUserCourse(user, pageable);
        return TourCourseResultListResponse.fromEntity(entities.map(TourCourseResponse::fromEntity), "OK");
    }

    public TourCourseResultListResponse readUserList(int userId, int pageNo, int pageSize) {
        User user = userRepository.findByIdxOrThrow(userId);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TourCourse> entities = tourCourseRepository.findPublicByUserCourse(user, pageable);
        return TourCourseResultListResponse.fromEntity(entities.map(TourCourseResponse::fromEntity), "OK");
    }

    /**
     * 장바구니 생성하기
     */
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

    /**
     * 내 장바구니 생성하기
     */
    public TourCourse createMyCourse(User user) {
        TourCourseRequest makeCourse = new TourCourseRequest();
        makeCourse.setUser(user);
        makeCourse.setTitle("장바구니");
        makeCourse.setState("cart");
        return tourCourseRepository.save(TourCourse.fromDto(makeCourse));
    }

    /**
     * 내 장바구니 수정하기
     * 코스에 포함된 관광지까지 전부 처리됨
     */
    public TourCourseResultResponse updateAll(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourCourseRepository.existsByIdx(request.getTour_course_idx())) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx());

            if (!tourCourse.getUserIdx().equals(user))
                return TourCourseResultResponse.fromEntity("AUTH_FAIL");

            tourCourse.update(request);

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

    /**
     * 내 코스 수정하기
     */
    @Transactional
    public TourCourseResultResponse update(TourCourseRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);

        if (tourCourseRepository.existsByIdx(request.getTour_course_idx())) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(request.getTour_course_idx());

            if (!tourCourse.getUserIdx().equals(user))
                return TourCourseResultResponse.fromEntity("AUTH_FAIL");

            tourCourse.update(request);
            return TourCourseResultResponse.fromEntity(tourCourse, "OK");
        }
        return TourCourseResultResponse.fromEntity("NOT_EXIST");
    }


}
