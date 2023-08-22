package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.utils.FormatUtils;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

@Data
public class UserResponse {

    private String token;
    private String email;
    private String loginType;
    private String nickName;
    private String gender;
    private String state;
    private String locale;
    private String dateJoin;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.token = user.getToken();
        response.email = FormatUtils.parseId(user.getId())[0];
        response.loginType = user.getLoginType();
        response.nickName = user.getNickName();
        response.gender = user.getGender().equals("m") ? "male" : "female";
        response.state = user.getState();
        response.locale = user.getLocale();
        response.dateJoin = TimeUtils.getLocalTime(user.getDateJoin());
        return response;
    }
}
