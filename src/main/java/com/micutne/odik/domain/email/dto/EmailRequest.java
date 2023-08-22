package com.micutne.odik.domain.email.dto;

import com.micutne.odik.utils.RandomUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailRequest {
    String email;
    String token;
    String code;
    LocalDateTime date;

    public EmailRequest(String email, String token, String code, LocalDateTime date) {
        this.email = email;
        this.token = token;
        this.code = code == null ? RandomUtils.Random6Hex() : code;
        this.date = LocalDateTime.now();
    }

}
