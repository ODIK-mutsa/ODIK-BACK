package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import com.micutne.odik.utils.FormatUtils;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String loginType;
    private String nickName;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.email = FormatUtils.parseId(user.getId())[0];
        response.loginType = user.getLoginType();
        response.nickName = user.getNickName();
        return response;
    }
}
