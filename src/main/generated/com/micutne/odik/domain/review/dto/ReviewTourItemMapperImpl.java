package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.domain.user.dto.ProfileResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-05T17:27:30+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class ReviewTourItemMapperImpl implements ReviewTourItemMapper {

    @Override
    public ReviewTourItem toEntity(ReviewTourItemRequest request) {
        if ( request == null ) {
            return null;
        }

        ReviewTourItem reviewTourItem = new ReviewTourItem();

        return reviewTourItem;
    }

    @Override
    public ReviewTourItem entityToDto(ReviewTourItem reviewTourItem) {
        if ( reviewTourItem == null ) {
            return null;
        }

        ReviewTourItem reviewTourItem1 = new ReviewTourItem();

        reviewTourItem1.setDateCreate( reviewTourItem.getDateCreate() );
        reviewTourItem1.setDateModify( reviewTourItem.getDateModify() );

        return reviewTourItem1;
    }

    @Override
    public ReviewTourItemResponse toDto(ReviewTourItem reviewTourItem) {
        if ( reviewTourItem == null ) {
            return null;
        }

        ReviewTourItemResponse reviewTourItemResponse = new ReviewTourItemResponse();

        reviewTourItemResponse.setIdx( reviewTourItem.getIdx() );
        reviewTourItemResponse.setRating( reviewTourItem.getRating() );
        reviewTourItemResponse.setContent( reviewTourItem.getContent() );
        reviewTourItemResponse.setUser( userToProfileResponse( reviewTourItem.getUser() ) );

        return reviewTourItemResponse;
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
