package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface ReviewItemMapper {
    ReviewItemMapper INSTANCE = Mappers.getMapper(ReviewItemMapper.class);

    @Mapping(source = "user_idx", target = "userIdx")
    @Mapping(source = "tour_item_idx", target = "tourItemIdx")

    ReviewItem toEntity(ReviewItemRequest request);
    ReviewItemListResponse toListDto(ReviewItem reviewItem);
    ReviewItem entityToDto(ReviewItem reviewItem);
    ReviewItemResponse toDto(ReviewItem reviewItem);
}
