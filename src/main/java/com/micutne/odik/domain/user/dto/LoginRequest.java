package com.micutne.odik.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    String id;
    String password;

    public LoginRequest() {
        id = "";
        password = "";
    }
}
