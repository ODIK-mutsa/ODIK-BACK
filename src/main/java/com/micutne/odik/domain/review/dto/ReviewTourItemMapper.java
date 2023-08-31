package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface ReviewTourItemMapper {
    ReviewTourItemMapper INSTANCE = Mappers.getMapper(ReviewTourItemMapper.class);

    @Mapping(source = "user_idx", target = "userIdx")
    @Mapping(source = "tour_item_idx", target = "tourItemIdx")
    ReviewTourItem toEntity(ReviewTourItemRequest request);
    ReviewTourItemListResponse toListDto(ReviewTourItem reviewTourItem);
    ReviewTourItem entityToDto(ReviewTourItem reviewTourItem);
    ReviewTourItemResponse toDto(ReviewTourItem reviewTourItem);
}
