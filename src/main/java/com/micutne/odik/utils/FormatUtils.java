package com.micutne.odik.utils;

import com.micutne.odik.domain.email.dto.EmailRequest;
import org.springframework.stereotype.Component;

@Component
public class FormatUtils {

    public static String formatId(String email, String loginType) {
        return String.format("%s&lt=%s", email, loginType);
    }

    public static String formatEmailToken(EmailRequest request) {
        return String.format("%s_%s", request.getEmail(), request.getDate().plusMinutes(3));
    }

    public static String[] parseEmailToken(String token) {
        return token.split("_");
    }
}
