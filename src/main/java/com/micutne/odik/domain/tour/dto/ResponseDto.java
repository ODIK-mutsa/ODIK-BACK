package com.micutne.odik.domain.tour.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String message;
    public static ResponseDto getMessage(String message) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        return responseDto;
    }

}
