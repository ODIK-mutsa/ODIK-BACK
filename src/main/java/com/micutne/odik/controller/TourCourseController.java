package com.micutne.odik.controller;


import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultResponse;
import com.micutne.odik.service.TourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour/course")
public class TourCourseController {
    private final TourCourseService tourCourseService;

    @RequestMapping(method = RequestMethod.GET, params = "course")
    public TourCourseResultResponse readOne(
            @RequestParam(name = "course") int course) {
        return tourCourseService.readOne(course);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<TourCourseResponse> readAll(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
            @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return tourCourseService.readAll(search, pageNo, pageSize);
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
