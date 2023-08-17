package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TourItemMapper {
    TourItemMapper INSTANCE = Mappers.getMapper(TourItemMapper.class);

    TourItem toEntity(TourItemRequest request);

    TourItemResponse toDto(TourItem tourItem);

    TourItemListResponse toListDto(TourItem tourItem);
}
