package com.micutne.odik.controller;

import com.micutne.odik.domain.user.dto.ProfileResponse;
import com.micutne.odik.domain.user.dto.UserRequest;
import com.micutne.odik.domain.user.dto.UserResponse;
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

    @GetMapping
    public UserResponse readMyInfo(Authentication authentication) {
        return userService.readOne(authentication.getPrincipal().toString());
    }

    @PostMapping("profile")
    public ProfileResponse readProfile(@RequestBody Long userIdx) {
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
}
