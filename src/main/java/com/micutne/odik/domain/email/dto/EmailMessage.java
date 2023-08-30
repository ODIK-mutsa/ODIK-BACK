package com.micutne.odik.domain.email.dto;

import lombok.Data;

@Data
public class EmailMessage {
    private String to;
    private String subject;
    private String message;

    public EmailMessage toMessage(String to, String subject, String message) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.to = to;
        emailMessage.subject = subject;
        emailMessage.message = message;
        return emailMessage;
    }
}