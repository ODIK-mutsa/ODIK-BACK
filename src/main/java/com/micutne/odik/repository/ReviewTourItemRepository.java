package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.review.ReviewTourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTourItemRepository extends JpaRepository<ReviewTourItem, Integer> {
    default ReviewTourItem findByIdOrThrow(int idx) {
        return findById(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_ITEM_NOT_FOUND));
    }


}
