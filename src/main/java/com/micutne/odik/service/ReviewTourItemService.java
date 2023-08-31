package com.micutne.odik.service;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.review.dto.ReviewTourItemRequest;
import com.micutne.odik.domain.review.dto.ReviewTourItemMapper;
import com.micutne.odik.domain.review.dto.ReviewTourItemResponse;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.repository.ReviewTourItemRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTourItemService {
    private final ReviewTourItemRepository reviewTourItemRepository;
    private final TourItemRepository tourItemRepository;
    private final ReviewTourItemMapper reviewTourItemMapper;
    private final UserRepository userRepository;


    /**
     *  특정 댓글 불러오기 (... 더보기 ?)
     */
    public ReviewTourItemResponse readOne(int itemIdx, int reviewIdx) {
        ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(reviewIdx);
        TourItem tourItem = tourItemRepository.findByIdOrThrow(itemIdx);
        return ReviewTourItemResponse.fromEntity(reviewTourItem);
    }

    /**
     * 전체 댓글 불러오기
     */

    /**
     * 관광지 저장
     */
    public ReviewTourItemResponse create(int tour_item_idx, ReviewTourItemRequest request, String username) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(tour_item_idx);
        request.setTour_item_idx(tourItem);
        request.setUser_idx(userRepository.findByIdOrThrow(username));
        ReviewTourItem reviewTourItem = reviewTourItemMapper.toEntity(request);
        reviewTourItem = reviewTourItemRepository.save(reviewTourItem);

        return com.micutne.odik.domain.review.dto.ReviewTourItemResponse.fromEntity(reviewTourItem);
    }




}

