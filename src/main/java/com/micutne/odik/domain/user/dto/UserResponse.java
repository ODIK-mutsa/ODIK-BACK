package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String loginType;
    private String nickName;
    private String gender;
    private String state;
    private String dateJoin;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.id = user.getId().split("&")[0];
        response.loginType = user.getLoginType();
        response.nickName = user.getNickName();
        response.gender = user.getGender().equals("m") ? "male" : "female";
        response.state = user.getState();
        response.dateJoin = TimeUtils.getLocalTime(user.getDateJoin());
        return response;
    }
}
