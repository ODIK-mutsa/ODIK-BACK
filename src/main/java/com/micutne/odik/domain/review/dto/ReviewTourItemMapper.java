package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface ReviewTourItemMapper {
    ReviewTourItemMapper INSTANCE = Mappers.getMapper(ReviewTourItemMapper.class);

    ReviewTourItem toEntity(ReviewTourItemRequest request);
    ReviewTourItem entityToDto(ReviewTourItem reviewTourItem);
    ReviewTourItemResponse toDto(ReviewTourItem reviewTourItem);

    default ReviewTourItemListResponse toListDto(ReviewTourItem reviewTourItem) {
        ReviewTourItemListResponse response = ReviewTourItemListResponse.builder()
                .idx(reviewTourItem.getIdx())
                .rating(reviewTourItem.getRating())
                .content(reviewTourItem.getContent())
                .user_idx(ProfileResponse.fromEntity(reviewTourItem.getUserIdx()))
                .tour_item_idx(TourItemResponse.fromEntity(reviewTourItem.getTourItemIdx()))
                .build();
        return response;
    }
}
