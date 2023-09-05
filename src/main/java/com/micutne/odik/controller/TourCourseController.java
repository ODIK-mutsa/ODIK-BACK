package com.micutne.odik.controller;


import com.micutne.odik.domain.review.dto.course.ReviewCoursePageResultResponse;
import com.micutne.odik.domain.review.dto.course.ReviewCourseRequest;
import com.micutne.odik.domain.review.dto.course.ReviewCourseResultResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultResponse;
import com.micutne.odik.service.ReviewTourCourseService;
import com.micutne.odik.service.TourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour/course")
public class TourCourseController {
    private final TourCourseService tourCourseService;
    private final ReviewTourCourseService reviewTourCourseService;

    @RequestMapping(method = RequestMethod.GET, params = "course")
    public TourCourseResultResponse readOne(
            @RequestParam(name = "course") int courseId) {
        return tourCourseService.readOne(courseId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<TourCourseResponse> readAll(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                            @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                            @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return tourCourseService.readAll(search, pageNo, pageSize);
    }


    @RequestMapping(value = "review", method = RequestMethod.GET, params = "course")
    public ReviewCoursePageResultResponse readReviewAll(@RequestParam(name = "course") int courseId,
                                                        @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                        @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return reviewTourCourseService.readCourse(courseId, pageNo, pageSize);
    }

    @RequestMapping(value = "review", method = RequestMethod.GET, params = "review")
    public ReviewCourseResultResponse readReviewOne(@RequestParam(name = "review") int reviewId) {
        return reviewTourCourseService.readReview(reviewId);
    }

    @PostMapping("/review")
    public ReviewCourseResultResponse createReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request) {
        return reviewTourCourseService.create(request, authentication.getPrincipal().toString());
    }

    @PutMapping("/review")
    public ReviewCourseResultResponse updateReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request) {
        return reviewTourCourseService.update(request, authentication.getPrincipal().toString());
    }

    @DeleteMapping("/review")
    public ReviewCourseResultResponse removeReview(
            Authentication authentication,
            @RequestBody ReviewCourseRequest request) {
        return reviewTourCourseService.delete(request, authentication.getPrincipal().toString());
    }
//    @PutMapping("/{course_id}")
//    public TourCourseResultResponse updateCourse(
//            Authentication authentication,
//            @PathVariable String course_id, @RequestBody )
//    {
//
//    }
//
//    @PutMapping("/{course_id}/review/{review_id}")
//    public TourCourseResultResponse updateCourseReview(
//            Authentication authentication,
//            @PathVariable String course_id, @PathVariable String review_id)
//    {
//
//    }
}
