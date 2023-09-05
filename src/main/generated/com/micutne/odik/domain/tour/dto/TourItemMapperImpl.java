package com.micutne.odik.domain.tour.dto;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-05T15:47:49+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class TourItemMapperImpl implements TourItemMapper {

    @Override
    public TourItem entityToDto(TourItem tourItem) {
        if ( tourItem == null ) {
            return null;
        }

        TourItem.TourItemBuilder tourItem1 = TourItem.builder();

        tourItem1.title( tourItem.getTitle() );
        tourItem1.user( tourItem.getUser() );
        tourItem1.location_lat( tourItem.getLocationLat() );
        tourItem1.location_lng( tourItem.getLocationLng() );
        tourItem1.address( tourItem.getAddress() );
        tourItem1.state( tourItem.getState() );
        tourItem1.reference_id_google( tourItem.getReferenceIdGoogle() );
        tourItem1.phone_number( tourItem.getPhoneNumber() );
        tourItem1.point_google( tourItem.getPointGoogle() );
        tourItem1.type( tourItem.getType() );

        return tourItem1.build();
    }

    @Override
    public TourItem toEntity(TourItemRequest request) {
        if ( request == null ) {
            return null;
        }

        TourItem.TourItemBuilder tourItem = TourItem.builder();

        tourItem.title( request.getTitle() );
        tourItem.user( request.getUser() );
        tourItem.location_lat( request.getLocation_lat() );
        tourItem.location_lng( request.getLocation_lng() );
        tourItem.state( request.getState() );
        tourItem.address( request.getAddress() );
        tourItem.reference_id_google( request.getReference_id_google() );
        tourItem.phone_number( request.getPhone_number() );
        tourItem.point_google( request.getPoint_google() );
        tourItem.type( request.getType() );

        return tourItem.build();
    }

    @Override
    public TourItemResponse toDto(TourItem tourItem) {
        if ( tourItem == null ) {
            return null;
        }

        TourItemResponse tourItemResponse = new TourItemResponse();

        tourItemResponse.setIdx( tourItem.getIdx() );
        tourItemResponse.setTitle( tourItem.getTitle() );
        tourItemResponse.setUser( userToProfileResponse( tourItem.getUser() ) );
        tourItemResponse.setState( tourItem.getState() );
        tourItemResponse.setAddress( tourItem.getAddress() );
        tourItemResponse.setType( tourItem.getType() );

        return tourItemResponse;
    }

    @Override
    public TourItemListResponse toListDto(TourItem tourItem) {
        if ( tourItem == null ) {
            return null;
        }

        TourItemListResponse tourItemListResponse = new TourItemListResponse();

        tourItemListResponse.setUser( tourItem.getUser() );
        tourItemListResponse.setState( tourItem.getState() );
        tourItemListResponse.setAddress( tourItem.getAddress() );
        tourItemListResponse.setType( tourItem.getType() );

        return tourItemListResponse;
    }

    protected ProfileResponse userToProfileResponse(User user) {
        if ( user == null ) {
            return null;
        }

        ProfileResponse profileResponse = new ProfileResponse();

        profileResponse.setIdx( user.getIdx() );
        profileResponse.setGender( user.getGender() );
        profileResponse.setLocale( user.getLocale() );

        return profileResponse;
    }
}
