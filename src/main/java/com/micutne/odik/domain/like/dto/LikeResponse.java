package com.micutne.odik.domain.like.dto;

import lombok.Data;

@Data
public class LikeResponse {
    boolean like;
    String result;

    public static LikeResponse toDto(boolean like, String result) {
        LikeResponse response = new LikeResponse();
        response.like = like;
        response.result = result;
        return response;
    }

    public static LikeResponse toDto(String result) {
        LikeResponse response = new LikeResponse();
        response.result = result;
        return response;
    }
}
