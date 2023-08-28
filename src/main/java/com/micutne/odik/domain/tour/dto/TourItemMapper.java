package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TourItemMapper {
    TourItemMapper INSTANCE = Mappers.getMapper(TourItemMapper.class);

            @Mapping(source = "title", target = "title")
            @Mapping(source = "user", target = "user")
            @Mapping(source = "locationLat", target = "location_lat")
            @Mapping(source = "locationLng", target = "location_lng")
            @Mapping(source = "address", target = "address")
            @Mapping(source = "state", target = "state")
            @Mapping(source = "referenceIdGoogle", target = "reference_id_google")
            @Mapping(source = "phoneNumber", target = "phone_number")
            @Mapping(source = "pointGoogle", target = "point_google")
           // @Mapping(source = "imagesGoogle", target = "images_google")

    TourItem entityToDto(TourItem tourItem);

    TourItem toEntity(TourItemRequest request);

   //@Mapping(source = "user", target = "user")
    TourItemResponse toDto(TourItem tourItem);

    TourItemListResponse toListDto(TourItem tourItem);
}


