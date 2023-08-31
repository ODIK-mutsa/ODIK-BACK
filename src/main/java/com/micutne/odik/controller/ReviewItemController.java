package com.micutne.odik.controller;

import com.micutne.odik.domain.review.dto.ReviewTourItemRequest;
import com.micutne.odik.domain.review.dto.ReviewTourItemResponse;
import com.micutne.odik.service.ReviewTourItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item/{itemId}/review")
public class ReviewItemController {
    private final ReviewTourItemService reviewTourItemService;

    /**
     *  특정 댓글 불러오기
     */
    @GetMapping("{reviewId}")
    public ReviewTourItemResponse readOne (
            @PathVariable("itemId") int itemId, @PathVariable("reviewId") int reviewId
    ) {
        return reviewTourItemService.readOne(itemId, reviewId);
    }

    /**
     * 전체 댓글 불러오기
     */

    /**
     * 댓글 생성
     */
    @PostMapping("")
    public ReviewTourItemResponse create(
            Authentication authentication,
            @PathVariable("itemId") int itemId,
            @RequestBody ReviewTourItemRequest request
            ) {
        return reviewTourItemService.create(itemId, request, authentication.getPrincipal().toString());
    }
}
