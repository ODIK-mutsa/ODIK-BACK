package com.micutne.odik.service;


import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.dto.TourCourseDto;
import com.micutne.odik.repository.TourCourseRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourCourseService {
    private final TourCourseRepository tourCourseRepository;
    private final UserRepository userRepository;


    // 등록
    public TourCourseDto createTourCourse(TourCourseDto dto) {
        TourCourse newTour = new TourCourse();
        newTour.setTitle(dto.getTitle());
        newTour.setUser_idx(dto.getUser_idx());
        newTour.setDate_create(dto.getDate_create());
        newTour.setDate_modify(dto.getDate_modify());
        return TourCourseDto.fromEntity(tourCourseRepository.save(newTour));
    }

    // 수정
    public TourCourseDto updateTourCourse(Long idx, TourCourseDto dto) {
        Optional<TourCourse> optionalTourCourse = TourCourseRepository.findById(idx);
        if (optionalTourCourse.isPresent()) {
            TourCourse tour = optionalTourCourse.get();
            tour.setUser_idx(dto.getUser_idx());
            tour.setTitle(dto.getTitle());
            tour.setDate_create(dto.getDate_create());
            tour.setDate_modify(dto.getDate_modify());
            return TourCourseDto.fromEntity(tourCourseRepository.save(tour));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

        // 삭제
    public void deleteTourCourse(Long idx) {
        if (tourCourseRepository.existsById(idx))
            tourCourseRepository.deleteById(idx);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
