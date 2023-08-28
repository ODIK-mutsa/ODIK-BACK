package com.micutne.odik.controller;


import com.micutne.odik.domain.tour.dto.ResponseDto;
import com.micutne.odik.domain.tour.dto.TourCourseDto;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.service.TourCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourCourseController {
    private final TourCourseRepository tourCourseRepository;
    private final TourCourseService tourCourseService;


    // 등록하기 / post / tour
    @PostMapping("/course")
    public ResponseDto create(@RequestBody TourCourseDto TourDto) {
        this.tourCourseService.create(TourDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("등록 되었습니다.");
        return responseDto;
    }

    // 수정하기 / put / tour
    @PutMapping("/course")
    public ResponseDto update(@PathVariable("idx") Long idx,
                              @RequestBody TourCourseDto dto) {
        tourCourseService.update(idx, dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("수정 되었습니다.");
        return responseDto;
    }

    // 삭제 / delete
    @DeleteMapping("/course")
    public ResponseDto delete(@PathVariable("idx") Long idx) {
        tourCourseService.delete(idx);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("삭제 되었습니다.");
        return responseDto;
    }
}
