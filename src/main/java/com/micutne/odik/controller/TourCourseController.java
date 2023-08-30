package com.micutne.odik.controller;


import com.micutne.odik.domain.tour.dto.ResponseDto;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.service.TourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour/course")
public class TourCourseController {
    private final TourCourseService tourCourseService;


    // 등록하기 / post / tour
    @PostMapping()
    public TourCourseResponse create(Authentication authentication, @RequestBody TourCourseRequest request) {
        return tourCourseService.create(request, authentication.getPrincipal().toString());
    }

    @PostMapping("/add_tour_item")
    public TourCourseResponse addTourItem(Authentication authentication, @RequestBody TourCourseRequest request) {
        return tourCourseService.create(request, authentication.getPrincipal().toString());
    }

    // 수정하기 / put / tour
    @PutMapping()
    public TourCourseResponse update(@PathVariable("idx") int idx,
                                     @RequestBody TourCourseRequest dto,
                                     Authentication authentication) {
        return tourCourseService.update(idx, dto, authentication.getPrincipal().toString());
    }

    // 삭제 / delete
    @DeleteMapping()
    public ResponseDto remove(@PathVariable("idx") int idx,
                              Authentication authentication) {
        tourCourseService.remove(idx, authentication.getPrincipal().toString());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("삭제 되었습니다.");
        return responseDto;
    }
}
