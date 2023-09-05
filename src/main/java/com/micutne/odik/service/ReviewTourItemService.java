package com.micutne.odik.service;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.review.dto.*;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.ReviewTourItemRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTourItemService {
    private final ReviewTourItemRepository reviewTourItemRepository;
    private final TourItemRepository tourItemRepository;
    private final UserRepository userRepository;

    /**
     *  특정 리뷰 불러오기
     */
    public ReviewTourItemResultResponse readReview(int reviewId) {
        if (reviewTourItemRepository.existsByIdx(reviewId)) {
            return ReviewTourItemResultResponse.fromEntity(reviewTourItemRepository.findByIdOrThrow(reviewId), "OK");
        }
        return ReviewTourItemResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }


    /**
     * 전체 리뷰 불러오기
     */
    public ReviewTourItemPageResponse readTourItem(int tourItemId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (tourItemRepository.existsById(tourItemId)) {
            TourItem tourItem = tourItemRepository.findByIdOrThrow(tourItemId);

            Page<ReviewTourItem> entities = reviewTourItemRepository.findAllByTourItem(tourItem, pageable);
            Page<ReviewTourItemResponse> dtos = entities.map(ReviewTourItemResponse::fromEntity);
            return ReviewTourItemPageResponse.fromPage("OK", dtos);
        }
        return ReviewTourItemPageResponse.fromPage("TOUR_ITEM_NOT_EXIST");
    }
    /**
     * 리뷰 생성
     */
    public ReviewTourItemResultResponse create(ReviewTourItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        int tourItemId = request.getTour_item_idx();
        if (tourItemRepository.existsById(tourItemId)) {
            TourItem tourItem = tourItemRepository.findByIdOrThrow(tourItemId);

            if (!tourItem.getState().equals("public"))
                return ReviewTourItemResultResponse.fromEntity("STATE_NOT_PUBLIC");

            if (!reviewTourItemRepository.existsByTourItemAndUser(tourItem, user)) {
                request.setUser(user);
                request.setTourItem(tourItem);

                ReviewTourItem reviewTourItem = reviewTourItemRepository.save(ReviewTourItem.fromDto(request));
                return ReviewTourItemResultResponse.fromEntity(reviewTourItem, "OK");
            }
            return ReviewTourItemResultResponse.fromEntity("ALREADY_CREATE");
        }
        return ReviewTourItemResultResponse.fromEntity("TOUR_ITEM_NOT_EXIST");
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public ReviewTourItemResultResponse update(ReviewTourItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);

        if (reviewTourItemRepository.existsByIdx(request.getReview_tour_item_idx())) {
            ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(request.getReview_tour_item_idx());

            TourItem tourItem = reviewTourItem.getTourItem();

            if (!tourItem.getState().equals("public"))
                return ReviewTourItemResultResponse.fromEntity("STATE_NOT_PUBLIC");

            if (!checkAuth(reviewTourItem, user))
                return ReviewTourItemResultResponse.fromEntity("AUTH_FAIL");

            reviewTourItem.update(request);
            return ReviewTourItemResultResponse.fromEntity(reviewTourItem, "OK");
        }
        return ReviewTourItemResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }


    /**
     * 리뷰 삭제
     */
    public ReviewTourItemResultResponse remove(ReviewTourItemRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);

        if (reviewTourItemRepository.existsByIdx(request.getReview_tour_item_idx())) {
            ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(request.getReview_tour_item_idx());
            TourItem tourItem = reviewTourItem.getTourItem();

            if (!tourItem.getState().equals("public"))
                return ReviewTourItemResultResponse.fromEntity("STATE_NOT_PUBLIC");
            if (!checkAuth(reviewTourItem, user))
                return ReviewTourItemResultResponse.fromEntity("AUTH_FAIL");

            reviewTourItemRepository.delete(reviewTourItem);
            return ReviewTourItemResultResponse.fromEntity("OK");
        }
        return ReviewTourItemResultResponse.fromEntity("REVIEW_NOT_EXIST");
    }

    // 리뷰 권한 확인
    public boolean checkAuth(ReviewTourItem reviewTourItem, User user) {
        return reviewTourItem.getUser().equals(user);
    }
}

