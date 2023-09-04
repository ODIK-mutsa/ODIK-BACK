package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.course.TourAddItemRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseRequest;
import com.micutne.odik.domain.tour.dto.course.TourCourseResultResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
import com.micutne.odik.service.AuthService;
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
    private final AuthService authService;
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
     * 사용자 장바구니에 관광지 추가하기
     */
    @PostMapping("course/add_tour_item")
    public TourCourseResultResponse addMyTourItem(Authentication authentication, @RequestBody TourAddItemRequest request) {
        return tourCourseService.addTourItem(request, authentication.getPrincipal().toString());
    }

    /**
     * 사용자 장바구니 수정하기
     */
    @PutMapping("course")
    public TourCourseResultResponse update(@RequestBody TourCourseRequest request, Authentication authentication) {
        return tourCourseService.updateCourse(request, authentication.getPrincipal().toString());
    }

//    /**
//     * 사용자 장바구니 삭제하기
//     */
//    @DeleteMapping("course")
//    public ResponseDto remove(@PathVariable("idx") int idx,
//                              Authentication authentication) {
//        tourCourseService.remove(idx, authentication.getPrincipal().toString());
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage("삭제 되었습니다.");
//        return responseDto;
//    }
}
