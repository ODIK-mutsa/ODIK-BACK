package com.micutne.odik.service;

import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.BusinessException;
import com.micutne.odik.common.exception.ErrorCode;
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
    private final ReviewTourItemMapper reviewTourItemMapper;
    private final UserRepository userRepository;
   // private final ReviewTourItemResultResponse reviewTourItemResultResponse;

    /**
     *  특정 리뷰 불러오기
     */
    /*
    public ReviewTourItemResponse readOne(int itemIdx, int reviewIdx) {
        ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(reviewIdx);
        TourItem tourItem = tourItemRepository.findByIdOrThrow(itemIdx);
        return ReviewTourItemResponse.fromEntity(reviewTourItem);
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
    /*
    public Page<ReviewTourItemPageResponse> readAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return reviewTourItemRepository.findAll(pageable).map(reviewTourItemMapper::toListDto);
    }

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
    /*
    public ReviewTourItemResponse create(int tour_item_idx, ReviewTourItemRequest request, String username) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(tour_item_idx);
        request.setTour_item_idx(tourItem);
        //request.setUser_idx(userRepository.findByIdOrThrow(username));
        ReviewTourItem reviewTourItem = reviewTourItemMapper.toEntity(request);
        //ReviewTourItem reviewTourItem = reviewTourItemMapper.requestToEntity(request);
        reviewTourItem = reviewTourItemRepository.save(reviewTourItem);

        return ReviewTourItemResponse.fromEntity(reviewTourItem);
    }

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
    /*
    public ReviewTourItemResponse update(int itemIdx, int reviewIdx, ReviewTourItemRequest request, String username) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(itemIdx);
        User user = userRepository.findByIdOrThrow(username);
        ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(reviewIdx);

        //checkAuth(reviewTourItem, user);
        checkPath(reviewTourItem, tourItem);
        reviewTourItem.update(request);
        //reviewTourItemResponse.setResult("OK");
        reviewTourItemRepository.save(reviewTourItem);

        return ReviewTourItemResponse.fromEntity(reviewTourItem);
    }

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
    /*
    public void remove(int itemId, int reviewId, String username) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(itemId);
        User user = userRepository.findByIdOrThrow(username);
        ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(reviewId);

        //checkAuth(reviewTourItem, user);
        checkPath(reviewTourItem, tourItem);
        try {
            reviewTourItemRepository.delete(reviewTourItem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.REVIEW_ITEM_DELETE_FAIL);
        }
        log.info("delete a review");
    }

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
        //if (!reviewTourItem.getUserIdx().equals(user)) throw new AuthException(ErrorCode.USER_NOT_FOUND);
        return reviewTourItem.getUser().equals(user);
    }


    // 리뷰 경로 확인
    /*
    public void checkPath(ReviewTourItem reviewTourItem, TourItem tourItem) {
        if (!reviewTourItem.getTourItemIdx().equals(tourItem)) throw new AuthException(ErrorCode.TOUR_ITEM_NOT_FOUND);
    }

     */
}

