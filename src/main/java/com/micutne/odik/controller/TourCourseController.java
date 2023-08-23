package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.ResponseDto;
import com.micutne.odik.domain.tour.dto.TourCourseDto;
import com.micutne.odik.service.TourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourCourseController {
    private final TourCourseService service;

    // 등록하기 / post / tour
    @PostMapping("/")
    public ResponseDto create(@RequestBody TourCourseDto TourDto) {
        this.service.createTourCourse(TourDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("등록되었습니다.");
        return responseDto;
    }

    // 수정하기 / put / tour
    @PutMapping("/")
    public ResponseDto update(@PathVariable("idx") Long idx,
                              @RequestBody TourCourseDto dto) {
        service.updateTourCourse(idx, dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("수정 되었습니다.");
        return responseDto;
    }

    // 삭제 / delete
    @DeleteMapping("/")
    public ResponseDto delete(@PathVariable("idx") Long idx) {
        service.deleteTourCourse(idx);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("삭제 되었습니다.");
        return responseDto;
    }
}
