package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String login_type;
    private String nick_name;
    private String locale;
    private String state;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.email = user.getId();
        response.login_type = user.getLoginType();
        response.nick_name = user.getNickName();
        response.locale = user.getLocale();
        response.state = user.getState();
        return response;
    }
}
