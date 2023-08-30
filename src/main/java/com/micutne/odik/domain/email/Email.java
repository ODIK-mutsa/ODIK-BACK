package com.micutne.odik.domain.email;

import com.micutne.odik.domain.email.dto.EmailRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 128)
    private String email;
    @Column
    private String token;
    private String code;
    private LocalDateTime date;

    public static Email fromDto(EmailRequest request) {
        Email emailEntity = new Email();
        emailEntity.email = request.getEmail();
        emailEntity.token = request.getToken();
        emailEntity.code = request.getCode();
        emailEntity.date = request.getDate();
        return emailEntity;
    }

    public void update(EmailRequest request) {
        token = request.getToken();
        code = request.getCode();
        date = request.getDate();
    }
}
