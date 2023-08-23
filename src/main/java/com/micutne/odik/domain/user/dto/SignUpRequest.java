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
    private String token;

    public SignUpRequest() {
        this.loginType = "email";
        this.state = "sign";
    }


}