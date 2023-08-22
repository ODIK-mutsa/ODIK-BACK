package com.micutne.odik.domain.email.dto;

import com.micutne.odik.domain.email.Email;
import com.micutne.odik.domain.email.EmailState;
import lombok.Data;

@Data
public class EmailResponse {
    EmailState result;
    String email;
    String token;

    public static EmailResponse fromEntity(Email emailEntity, EmailState result) {
        EmailResponse response = new EmailResponse();
        response.email = emailEntity.getEmail();
        response.token = emailEntity.getToken();
        response.result = result;
        return response;
    }

    public static EmailResponse fromEntity(String email, EmailState result) {
        EmailResponse response = new EmailResponse();
        response.email = email;
        response.result = result;
        return response;
    }
}
