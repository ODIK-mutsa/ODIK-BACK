package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.service.TourCourseService;
import com.micutne.odik.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final TourCourseService tourCourseService;


    @GetMapping
    public UserResponse readMyInfo(Authentication authentication) {
        return userService.readOne(authentication.getPrincipal().toString());
    }

    @PostMapping("profile")
    public ProfileResponse readProfile(@RequestBody int userIdx) {
        return userService.readProfile(userIdx);
    }

    @PutMapping
    public UserResponse updateMyInfo(Authentication authentication, @RequestBody UserRequest request) {
        return userService.updateInfo(request, authentication.getPrincipal().toString());
    }

    @PutMapping("state")
    public UserResponse updateMyState(Authentication authentication, @RequestBody UserRequest request) {
        return userService.updateState(request, authentication.getPrincipal().toString());
    }

    /**
     * 사용자 장바구니 불러오기
     */
    @GetMapping("course")
    public TourCourseResultResponse readMyCourse(Authentication authentication) {
        return tourCourseService.readMyCourse(authentication.getPrincipal().toString());
    }

    /**
     * 사용자 코스 생성하기
     */
    @PostMapping("course")
    public TourCourseResultResponse create(Authentication authentication, @RequestBody TourCourseRequest request) {
        return tourCourseService.create(request, authentication.getPrincipal().toString());
    }


    /**
     * 사용자 장바구니 수정하기
     */
    @PutMapping("course")
    public TourCourseResultResponse update(@RequestBody TourAddItemRequest request, Authentication authentication) {
        return tourCourseService.updateAll(request, authentication.getPrincipal().toString());
    }

}