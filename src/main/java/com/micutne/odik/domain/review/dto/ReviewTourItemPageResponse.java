package com.micutne.odik.domain.review.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ReviewTourItemPageResponse {
    /*
    int idx;
    float rating;
    String content;
  //  ProfileResponse user_idx;
    TourItemResponse tour_item_idx;
   // String result;

     */
    String result;
    Page<ReviewTourItemResponse> review_tour_item;
/*
    @Builder
    public ReviewTourItemListResponse(int idx, float rating, String content, ProfileResponse user_idx, TourItemResponse tour_item_idx) {
        this.idx = idx;
        this.rating = rating;
        this.content = content;
    //    this.user_idx = user_idx;
        this.tour_item_idx = tour_item_idx;
    }

 */

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
