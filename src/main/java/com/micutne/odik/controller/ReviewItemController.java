package com.micutne.odik.controller;

import com.micutne.odik.domain.review.dto.ReviewTourItemPageResponse;
import com.micutne.odik.domain.review.dto.ReviewTourItemRequest;
import com.micutne.odik.domain.review.dto.ReviewTourItemResultResponse;
import com.micutne.odik.service.ReviewTourItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item/review")
public class ReviewItemController {
    private final ReviewTourItemService reviewTourItemService;

    /**
     *  특정 리뷰 불러오기
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = "review")
    public ReviewTourItemResultResponse readReviewOne(@RequestParam(name = "review") int reviewId) {
        return reviewTourItemService.readReview(reviewId);
    }

    /**
     * 전체 리뷰 불러오기
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = "item")
    public ReviewTourItemPageResponse readReviewAll(@RequestParam(name = "item") int tourItemId,
                                                    @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                    @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return reviewTourItemService.readTourItem(tourItemId, pageNo, pageSize);
    }


    /**
     * 리뷰 생성
     */
    @PostMapping("")
    public ReviewTourItemResultResponse createReview(
            Authentication authentication,
            @RequestBody ReviewTourItemRequest request
            ) {
        return reviewTourItemService.create(request, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("")
    public ReviewTourItemResultResponse updateReview(
            Authentication authentication,
            @RequestBody ReviewTourItemRequest request
    ) {
        return reviewTourItemService.update(request, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("")
    public void removeReview(
            Authentication authentication,
            @RequestBody ReviewTourItemRequest request
    ) {
        reviewTourItemService.remove(request, authentication.getPrincipal().toString());
    }


}
