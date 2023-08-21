package com.micutne.odik.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    private static String email;

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
