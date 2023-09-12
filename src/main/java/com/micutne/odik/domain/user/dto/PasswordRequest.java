package com.micutne.odik.domain.user.dto;

import lombok.Data;

@Data
public class PasswordRequest {
    String id;
    String password_old;
    String password_new;
}
