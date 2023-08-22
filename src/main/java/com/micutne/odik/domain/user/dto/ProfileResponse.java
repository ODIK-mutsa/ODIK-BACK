package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.Data;

@Data
public class ProfileResponse {
    Long idx;
    String nickName;
    String gender;
    String locale;

    public static ProfileResponse fromEntity(User user) {
        ProfileResponse response = new ProfileResponse();
        response.idx = user.getIdx();
        response.nickName = user.getNickName();
        response.gender = user.getGender().equals("m") ? "male" : "female";
        response.locale = user.getLocale();
        return response;
    }
}
