package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class UserResultResponse {
    String result;
    UserResponse user;

    public static UserResultResponse fromEntity(User user, String result) {
        UserResultResponse response = new UserResultResponse();
        response.result = result;
        response.user = UserResponse.fromEntity(user);
        return response;
    }

    public static UserResultResponse fromEntity(String result) {
        UserResultResponse response = new UserResultResponse();
        response.result = result;
        return response;
    }
}
