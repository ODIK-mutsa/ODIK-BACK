package com.micutne.odik.domain.user.dto;

import com.micutne.odik.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    String result;
    String token_odik;
    String nick_name;
    String gender;
    String locale;

    public LoginResponse(String result) {
        this.result = result;
    }

    public static LoginResponse fromEntity(User user, String result) {
        LoginResponse response = new LoginResponse();
        response.result = result;
        response.token_odik = user.getToken();
        response.nick_name = user.getNickName();
        response.gender = user.getGender();
        response.locale = user.getLocale();
        return response;
    }
}
