package com.micutne.odik.controller;

import com.micutne.odik.domain.review.dto.ReviewTourItemListResponse;
import com.micutne.odik.domain.review.dto.ReviewTourItemRequest;
import com.micutne.odik.domain.review.dto.ReviewTourItemResponse;
import com.micutne.odik.service.ReviewTourItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item/{itemId}/review")
public class ReviewItemController {
    private final ReviewTourItemService reviewTourItemService;

    /**
     *  특정 리뷰 불러오기
     */
    @GetMapping("{reviewId}")
    public ReviewTourItemResponse readOne (
            @PathVariable("itemId") int itemId, @PathVariable("reviewId") int reviewId
    ) {
        return reviewTourItemService.readOne(itemId, reviewId);
    }

    /**
     * 전체 리뷰 불러오기
     */
    @GetMapping("")
    public Page<ReviewTourItemListResponse> readAll(
            @PathVariable("itemId") int itemId,
            @RequestParam(name = "no", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "20") int pageSize) {
        return reviewTourItemService.readAll(pageNo, pageSize);
    }


    /**
     * 리뷰 생성
     */
    @PostMapping("")
    public ReviewTourItemResponse create(
            Authentication authentication,
            @PathVariable("itemId") int itemId,
            @RequestBody ReviewTourItemRequest request
            ) {
        return reviewTourItemService.create(itemId, request, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("{reviewId}")
    public ReviewTourItemResponse update(
            Authentication authentication,
            @PathVariable("itemId") int itemId,
            @PathVariable("reviewId") int reviewId,
            @RequestBody ReviewTourItemRequest request
    ) {
        return reviewTourItemService.update(itemId, reviewId, request, authentication.getPrincipal().toString());
    }
}
