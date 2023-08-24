package com.micutne.odik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    private static String email;

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        TokenConfig.email = email;
    }
}
