package com.micutne.odik.domain.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    String nickName;
    String gender;
    String state;
    String locale;

    public UserRequest(String nickName, String gender, String state, String locale) {
        this.nickName = nickName;
        this.gender = gender;
        this.state = state;
        this.locale = locale == null ? "en" : locale;
    }
}
