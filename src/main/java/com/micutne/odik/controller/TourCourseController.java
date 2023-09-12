package com.micutne.odik.controller;


import com.micutne.odik.domain.like.dto.CourseLikeRequest;
import com.micutne.odik.domain.like.dto.LikeResponse;
import com.micutne.odik.domain.review.dto.course.ReviewCoursePageResultResponse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseRequest;
import com.micutne.odik.domain.review.dto.course.ReviewCourseResultResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultListResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultResponse;
import com.micutne.odik.service.HistoryLikeCourseService;
import com.micutne.odik.service.ReviewTourCourseService;
import com.micutne.odik.service.TourCourseService;
import com.micutne.odik.utils.redis.SearchRedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/course")
public class TourCourseController {
    private final TourCourseService tourCourseService;
    private final ReviewTourCourseService reviewTourCourseService;
    private final HistoryLikeCourseService historyLikeCourseService;


    /**
     * 코스 단일 출력
     */
    @GetMapping("{course_id}")
    public TourCourseResultResponse readOne(@PathVariable int course_id) {
        return tourCourseService.readOne(course_id);
    }

    /**
     * 검색 및 전체 출력
     */
    @GetMapping()
    public TourCourseResultListResponse readAll(@RequestParam(name = "keyword", required = false, defaultValue = "") String search,
                                                @RequestParam(name = "order", required = false, defaultValue = "like") String orderBy,
                                                @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        search = URLDecoder.decode(search, StandardCharsets.UTF_8);
        String[] keywords = search.split(" ");
        SearchRedisUtils.addSearchKeyword(keywords);
        return tourCourseService.searchAll(keywords, orderBy, pageNo, pageSize);
    }

    /**
     * 사용자의 코스 출력
     */
    @GetMapping("/user/{user_id}")
    public TourCourseResultListResponse readUserList(@PathVariable int user_id,
                                                     @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                     @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return tourCourseService.readUserList(user_id, pageNo, pageSize);
    }

    /**
     * 코스 수정
     */
    @PutMapping("")
    public TourCourseResultResponse updateCourse(Authentication authentication,
                                                 @RequestBody TourCourseRequest request) {
        return tourCourseService.update(request, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 불러오기
     */
    @GetMapping("{course_id}/review")
    public ReviewCoursePageResultResponse readReviewAll(@RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                        @RequestParam(name = "page_size", defaultValue = "20") int pageSize,
                                                        @PathVariable int course_id) {
        return reviewTourCourseService.readCourse(course_id, pageNo, pageSize);
    }

    /**
     * 리뷰 단일 불러오기
     */
    @RequestMapping(value = "review", method = RequestMethod.GET, params = "review")
    public ReviewCourseResultResponse readReviewOne(@RequestParam(name = "review") int reviewId) {
        return reviewTourCourseService.readReview(reviewId);
    }

    /**
     * 리뷰 작성
     */
    @PostMapping("/{course_id}/review")
    public ReviewCourseResultResponse createReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request, @PathVariable int course_id) {
        return reviewTourCourseService.create(course_id, request, authentication.getPrincipal().toString());
    }

    @PutMapping("/{course_id}/review/{review_id}")
    public ReviewCourseResultResponse updateReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request, @PathVariable int course_id, @PathVariable int review_id) {
        return reviewTourCourseService.update(course_id, review_id, request, authentication.getPrincipal().toString());
    }

    @DeleteMapping("/review")
    public ReviewCourseResultResponse removeReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request) {
        return reviewTourCourseService.delete(request, authentication.getPrincipal().toString());
    }

    @GetMapping("/{course_id}/like")
    public LikeResponse readLike(Authentication authentication,
                                 @PathVariable int course_id) {
        return historyLikeCourseService.read(course_id, authentication.getPrincipal().toString());
    }

    @PostMapping("/{course_id}/like")
    public LikeResponse updateLike(Authentication authentication,
                                   @PathVariable int course_id,
                                   @RequestBody CourseLikeRequest request) {
        return historyLikeCourseService.update(course_id, request, authentication.getPrincipal().toString());
    }

}