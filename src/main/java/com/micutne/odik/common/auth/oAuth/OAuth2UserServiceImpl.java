package com.micutne.odik.common.auth.oAuth;

import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("오는거 맞냐");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();           // application.yaml 에 등록한 id가 나온다.

        String nameAttribute = "";

        Map<String, Object> attributes = new HashMap<>();
        switch (registrationId) {
            case "google" -> {
                log.info("google oAuth 요청");
                attributes.put("loginType", registrationId);
                attributes.put("id", oAuth2User.getAttribute("email"));
                attributes.put("name", oAuth2User.getAttribute("name"));
                attributes.put("locale", oAuth2User.getAttribute("locale"));
                nameAttribute = "id";
            }
            case "apple" -> {
                log.info("apple oAuth 요청");
                attributes.put("loginType", registrationId);
                nameAttribute = "id";
            }
            default -> {
                throw new AuthException(ErrorCode.AUTH_NOT_FOUND, registrationId);
            }
        }

        // 여기까지 오면 인증 성공
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                nameAttribute
        );
    }
}
