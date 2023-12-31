package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ProfileResponse {
    int idx;
    String nick_name;
    String gender;
    String locale;

    public static ProfileResponse fromEntity(User user) {
        ProfileResponse response = new ProfileResponse();
        response.idx = user.getIdx();
        response.nick_name = user.getNickName();
        if (user.getGender() != null) {
            response.gender = user.getGender();
        }
        response.locale = user.getLocale();
        return response;
    }
}
