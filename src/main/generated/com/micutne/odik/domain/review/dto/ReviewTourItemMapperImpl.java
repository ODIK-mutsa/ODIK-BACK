package com.micutne.odik.domain.review.dto;

import com.micutne.odik.domain.review.ReviewTourItem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-04T15:53:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class ReviewTourItemMapperImpl implements ReviewTourItemMapper {

    @Override
    public ReviewTourItem toEntity(ReviewTourItemRequest request) {
        if ( request == null ) {
            return null;
        }

        ReviewTourItem.ReviewTourItemBuilder reviewTourItem = ReviewTourItem.builder();

        reviewTourItem.rating( request.getRating() );
        reviewTourItem.content( request.getContent() );
        reviewTourItem.user_idx( request.getUser_idx() );
        reviewTourItem.tour_item_idx( request.getTour_item_idx() );

        return reviewTourItem.build();
    }

    @Override
    public ReviewTourItem entityToDto(ReviewTourItem reviewTourItem) {
        if ( reviewTourItem == null ) {
            return null;
        }

        ReviewTourItem.ReviewTourItemBuilder reviewTourItem1 = ReviewTourItem.builder();

        reviewTourItem1.rating( reviewTourItem.getRating() );
        reviewTourItem1.content( reviewTourItem.getContent() );

        return reviewTourItem1.build();
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

        return reviewTourItemResponse;
    }
}
