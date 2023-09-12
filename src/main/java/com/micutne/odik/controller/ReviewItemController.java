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
@RequestMapping("/tour/item")
public class ReviewItemController {
    private final ReviewTourItemService reviewTourItemService;

    /**
     *  특정 리뷰 불러오기
     */
    @GetMapping("/{item_id}/review/{review_id}")
    public ReviewTourItemResultResponse readReviewOne(@PathVariable int item_id,
                                                      @PathVariable int review_id)
    {
        return reviewTourItemService.readReview(item_id, review_id);
    }

    /**
     * 전체 리뷰 불러오기
     */
    @GetMapping("/{item_id}/review")
    public ReviewTourItemPageResponse readReviewAll(@PathVariable int item_id,
                                                    @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
                                                    @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {
        return reviewTourItemService.readTourItem(item_id, pageNo, pageSize);
    }


    /**
     * 리뷰 생성
     */
    @PostMapping("/{item_id}/review")
    public ReviewTourItemResultResponse createReview(
            Authentication authentication,
            @PathVariable int item_id,
            @RequestBody ReviewTourItemRequest request
            ) {
        return reviewTourItemService.create(request, item_id, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/{item_id}/review/{review_id}")
    public ReviewTourItemResultResponse updateReview(
            Authentication authentication,
            @RequestBody ReviewTourItemRequest request,
            @PathVariable int item_id,
            @PathVariable int review_id
    ) {
        return reviewTourItemService.update(item_id, review_id, request, authentication.getPrincipal().toString());
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{item_id}/review/{review_id}")
    public ReviewTourItemResultResponse removeReview(
            Authentication authentication,
            @RequestBody ReviewTourItemRequest request,
            @PathVariable int item_id,
            @PathVariable int review_id
    ) {
        return reviewTourItemService.remove(item_id, review_id, request, authentication.getPrincipal().toString());
    }


}
