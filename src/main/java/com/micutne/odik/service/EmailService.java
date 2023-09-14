package com.micutne.odik.service;

import com.micutne.odik.config.TokenConfig;
import com.micutne.odik.domain.email.Email;
import com.micutne.odik.domain.email.EmailState;
import com.micutne.odik.domain.email.dto.EmailRequest;
import com.micutne.odik.domain.email.dto.EmailResponse;
import com.micutne.odik.repository.EmailRepository;
import com.micutne.odik.repository.UserRepository;
import com.micutne.odik.utils.FormatUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailService {
    private final StandardPBEStringEncryptor tokenEncoder;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public EmailService(StandardPBEStringEncryptor tokenEncoder, EmailRepository emailRepository, UserRepository userRepository, JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.tokenEncoder = tokenEncoder;
        tokenEncoder.setPassword(TokenConfig.getEmail());
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    //비밀번호 변경 메일 전송
    @Transactional
    public EmailResponse passwordVerifyRequest(EmailRequest request) {
        String MAIL_TITLE = "[ODIK] PASSWORD VERIFY";
        String MAIL_TEMPLATE = "passwordVerify";

        String userId = FormatUtils.formatId(request.getEmail(), "email");
        if (userRepository.existsById(userId)) {
            return VerifyRequest(request, MAIL_TITLE, MAIL_TEMPLATE);
        } else {
            return EmailResponse.fromEntity(request.getEmail(), EmailState.NOT_EXIST);
        }
    }

    //이메일 확인 메일 전송
    @Transactional
    public EmailResponse emailVerifyRequest(EmailRequest request) {
        String MAIL_TITLE = "[ODIK] EMAIL VERIFY";
        String MAIL_TEMPLATE = "emailVerify";

        String userId = FormatUtils.formatId(request.getEmail(), "email");
        //사용중인 계정인지 확인
        if (!userRepository.existsById(userId)) {
            return VerifyRequest(request, MAIL_TITLE, MAIL_TEMPLATE);
        } else {
            return EmailResponse.fromEntity(request.getEmail(), EmailState.ALREADY_EXIST);
        }

    }

    //메일 전송 본체
    @Transactional
    public EmailResponse VerifyRequest(EmailRequest request, String MAIL_TITLE, String MAIL_TEMPLATE) {
        //메일 전송
        try {
            sendMessage(request.getEmail(), MAIL_TITLE, request.getCode(), MAIL_TEMPLATE);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        //토큰 생성
        request.setToken(tokenEncoder.encrypt(FormatUtils.formatEmailToken(request)));

        Email emailEntity;
        //이미 인증 번호 메일을 발송한 경우
        if (emailRepository.existsByEmail(request.getEmail())) {
            emailEntity = emailRepository.findByEmailOrThrow(request.getEmail());
            emailEntity.update(request);
        }
        //새로 메일을 발송하는 경우
        else {
            emailEntity = emailRepository.save(Email.fromDto(request));
            emailRepository.save(emailEntity);
        }
        return EmailResponse.fromEntity(emailEntity, EmailState.SEND);
    }

    /**
     * 인증번호 확인
     */
    public EmailResponse verifyCheck(EmailRequest request) {
        //만료된 인증번호인지 확인
        if (isExpired(request.getToken())) {
            return EmailResponse.fromEntity(request.getEmail(), EmailState.EXPIRED);
        }
        Email emailEntity = emailRepository.findByTokenOrThrow(request.getToken());
        if (emailEntity.getCode().equals(request.getCode())) {
            emailRepository.delete(emailEntity);
            return EmailResponse.fromEntity(emailEntity, EmailState.OK);
        } else return EmailResponse.fromEntity(emailEntity, EmailState.WRONG_CODE);
    }


    /**
     * 메일 전송
     */
    public void sendMessage(String to, String subject, String code, String templatePath) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);

        // Thymeleaf 값 삽입
        Context context = new Context();
        context.setVariable("code", code);

        // Process the template
        String htmlContent = templateEngine.process(templatePath, context);
        helper.setText(htmlContent, true); // HTML content 삽입

        javaMailSender.send(message);
    }

    /**
     * 유효기간 만료 확인
     */
    private boolean isExpired(String token) {
        String parseToken = FormatUtils.parseEmailToken(tokenEncoder.decrypt(token))[1];
        LocalDateTime expireDate = LocalDateTime.parse(parseToken);
        LocalDateTime now = LocalDateTime.now();

        return expireDate.isBefore(now);
    }

}
