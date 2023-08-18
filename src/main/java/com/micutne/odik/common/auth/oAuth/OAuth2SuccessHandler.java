package com.micutne.odik.common.auth.oAuth;

import com.micutne.odik.common.auth.CustomUserDetails;
import com.micutne.odik.common.auth.CustomUserDetailsManager;
import com.micutne.odik.domain.user.dto.LoginResponse;
import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomUserDetailsManager userDetailsManager;
    private final AuthService authService;

    @Override
    // 인증 성공시 호출되는 메소드
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String loginType = oAuth2User.getAttribute("loginType");
        String email = oAuth2User.getAttribute("id");
        String id = email + "&lt=" + loginType;
        String name = oAuth2User.getAttribute("name");
        String locale = oAuth2User.getAttribute("locale");

        // 처음으로 소셜 로그인한 사용자를 데이터베이스에 등록
        if (!userDetailsManager.userExists(id)) {
            SignUpRequest signUpRequest = new SignUpRequest(email, loginType, name, null, locale, null, null);
            authService.signUpOAuth(signUpRequest);
        }

        // 토큰 발행
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsManager.loadUserByUsername(id);
        LoginResponse loginResponse = authService.getTokens(userDetails);

        // 목적지 URL 설정
        // 우리 서비스의 Frontend 구성에 따라 유연하게 대처해야 한다.
        String targetUrl = "http://localhost:8080/auth/result"
                + "?access="
                + loginResponse.getAccessToken()
                + "&refresh="
                + loginResponse.getRefreshToken();

        // 실제 Redirect 응답 생성
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
