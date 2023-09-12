package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewTourItemRepository extends JpaRepository<ReviewTourItem, Integer> {
    default ReviewTourItem findByIdOrThrow(int idx) {
        return findById(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_ITEM_NOT_FOUND));
    }

    Page<ReviewTourItem> findAllBy(Pageable pageable);

    Page<ReviewTourItem> findAll(Pageable pageable);

    Optional<ReviewTourItem> findByIdx(int idx);

    Boolean existsByIdx(int idx);
    Boolean existsByTourItemAndUser(TourItem tourItem, User user);
    Page<ReviewTourItem> findAllByTourItem(TourItem tourItem, Pageable pageable);
    Page<ReviewTourItem> findAllByUser(User user, Pageable pageable);
}
