package com.micutne.odik.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    private String id;
    private String loginType;
    @Size(min = 20)
    private String nickName;
    @Size(min = 1)
    private String gender;
    private String locale;
    private String state;
    private String phone;
    private String password;
    private String pss;

    public SignUpRequest(String id, String loginType, String nickName, String gender, String locale, String phone, String password) {
        this.id = id;
        this.loginType = loginType == null ? "email" : loginType;
        this.nickName = nickName;
        this.gender = gender;
        this.locale = locale;
        this.state = "sign";
        this.phone = phone;
        this.password = password;
    }


}
