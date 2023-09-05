package com.micutne.odik.domain.review.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ReviewTourItemPageResponse {

    String result;
    Page<ReviewTourItemResponse> review_tour_item;

    public static ReviewTourItemPageResponse fromPage(String result, Page<ReviewTourItemResponse> review_tour_item) {
        ReviewTourItemPageResponse response = new ReviewTourItemPageResponse();
        response.result = result;
        response.review_tour_item = review_tour_item;
        return response;
    }

    public static ReviewTourItemPageResponse fromPage(String result) {
        ReviewTourItemPageResponse response = new ReviewTourItemPageResponse();
        response.result = result;
        return response;
    }


}
