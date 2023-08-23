package com.micutne.odik.domain.user.dto;

import lombok.Data;

@Data
public class CheckResponse {
    String result;

    public CheckResponse(String result) {
        this.result = result;
    }
}
