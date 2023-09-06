package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourItemRepository extends JpaRepository<TourItem, Integer> {
    default TourItem findByIdOrThrow(int idx) {
        return findById(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOUR_ITEM_NOT_FOUND));
    }

    Boolean existsByReferenceIdGoogle(String referenceIdGoogle);

    TourItem findByReferenceIdGoogle (String referenceIdGoogle);

    Page<TourItem> findByUserIdxOrderByDateCreateDesc(ProfileResponse user, Pageable pageable);

    Page<TourItem> findAllBy(Pageable pageable);

    Page<TourItem> findAllByState(String state, Pageable pageable);

    Page<TourItem> findAllByUserIdxInOrderByDateCreateDesc(List<User> user, Pageable pageable);

    Page<TourItem> findAllByTitleContains(String query, Pageable pageable);

    Page<TourItem> findAllByType(String query, Pageable pageable);

    Page<TourItem> findAll(Specification<TourItem> spec, Pageable pageable);

}