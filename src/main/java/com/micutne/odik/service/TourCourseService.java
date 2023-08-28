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


    public TourCourseDto create(TourCourseDto dto) {
        TourCourse newTour = new TourCourse();
        newTour.getTitle();
        newTour.getUserIdx();
        newTour.getDateCreate();
        newTour.getDateModify();
        return TourCourseDto.fromEntity(tourCourseRepository.save(newTour));
    }

    // 수정
    public TourCourseDto update(Long idx, TourCourseDto dto) {
        Optional<TourCourse> optionalTourCourse = tourCourseRepository.findById(idx);
        if (optionalTourCourse.isPresent()) {
            TourCourse tour = optionalTourCourse.get();
            tour.getUserIdx();
            tour.getTitle();
            tour.getDateCreate();
            tour.getDateModify();
            return TourCourseDto.fromEntity(tourCourseRepository.save(tour));
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    // 삭제
    public void delete(Long idx) {
        if (tourCourseRepository.existsById(idx))
            tourCourseRepository.deleteById(idx);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
