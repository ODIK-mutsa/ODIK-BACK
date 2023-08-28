package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTourItemRepository extends JpaRepository<ImageTourItem, Long> {
    default ImageTourItem findByIdOrThrow(Long idx) {
        return findById(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_TOUR_ITEM_NOT_FOUND));
    }

    Boolean existsByIdx(Long idx);

}


