package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.utils.TimeUtils;
import lombok.Data;

@Data
public class UserResponse {
    int idx;
    String email;
    String login_type;
    String token_odik;
    String nick_name;
    String gender;
    String locale;
    String state;
    String date_join;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.idx = user.getIdx();
        response.email = user.getId();
        response.login_type = user.getLoginType();
        response.token_odik = user.getToken();
        response.nick_name = user.getNickName();
        response.gender = user.getGender();
        response.locale = user.getLocale();
        response.state = user.getLocale();
        response.date_join = TimeUtils.getLocalTime(user.getDateJoin());
        return response;
    }
}
