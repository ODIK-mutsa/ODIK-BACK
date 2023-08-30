package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.sound.sampled.ReverbType;

@Component
@Mapper(componentModel = "spring")
public interface ReviewItemMapper {
    ReviewItemMapper INSTANCE = Mappers.getMapper(ReviewItemMapper.class);

    @Mapping(source = "userIdx", target = "user_idx")
    @Mapping(source = "tourItemIdx", target = "tour_item_idx")

    ReviewItem toEntity(ReviewItemRequest request);
    ReviewItemListResponse toListDto(ReviewItem reviewItem);
    ReviewItem entityToDto(ReviewItem reviewItem);
    ReviewItemResponse toDto(ReviewItem reviewItem);
}
