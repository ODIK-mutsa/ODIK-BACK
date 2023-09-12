package com.micutne.odik.utils.file;

import lombok.Data;

import java.util.List;

@Data
public class FileResultResponse {
    String result;
    List<String> urls;

    public static FileResultResponse toDto(String result, List<String> urls) {
        FileResultResponse response = new FileResultResponse();
        response.result = result;
        response.urls = urls;
        return response;
    }

    public static FileResultResponse toDto(String result) {
        FileResultResponse response = new FileResultResponse();
        response.result = result;
        return response;
    }
}
