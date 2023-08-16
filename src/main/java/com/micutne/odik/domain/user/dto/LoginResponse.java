package com.micutne.odik.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    public String accessToken;
    public String refreshToken;

}
