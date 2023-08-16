package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String nickName;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.nickName = user.getNickName();
        return response;
    }
}
