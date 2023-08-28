package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourItemRepository extends JpaRepository<TourItem, Long> {
    default TourItem findByIdOrThrow(Long idx) {
        return findById(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_ITEM_NOT_FOUND));
    }

    Boolean existsByReferenceIdGoogle(String referenceIdGoogle);

    Page<TourItem> findByUserIdxOrderByDateCreateDesc(User user, Pageable pageable);

    Page<TourItem> findAllBy(Pageable pageable);

    Page<TourItem> findAllByUserIdxInOrderByDateCreateDesc(List<User> user, Pageable pageable);

}


