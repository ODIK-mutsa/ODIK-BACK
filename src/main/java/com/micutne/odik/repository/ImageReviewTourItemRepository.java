package com.micutne.odik.repository;

import com.micutne.odik.domain.images.ImageReviewTourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageReviewTourItemRepository extends JpaRepository<ImageReviewTourItem, Long> {
}
