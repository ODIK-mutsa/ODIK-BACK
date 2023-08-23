package com.micutne.odik.config;

import com.micutne.odik.common.auth.JwtTokenFilter;
import com.micutne.odik.common.auth.oAuth.OAuth2SuccessHandler;
import com.micutne.odik.common.auth.oAuth.OAuth2UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@Slf4j
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2UserServiceImpl oAuth2UserService;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter, OAuth2SuccessHandler oAuth2SuccessHandler, OAuth2UserServiceImpl oAuth2UserService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
    }


    @Bean //메소드의 결과를 @Bean 객체로 등록해주는 어노테이션
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //CSRF : 사이트 사이간 위조 방지 해제 (disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authHttp -> authHttp
//                                .requestMatchers().permitAll()        //누구든 허용
//                                .requestMatchers().authenticated()    //인증된 사용자만 허용
//                                .requestMatchers("/auth/**").anonymous()        //인증되지 않은 사용자만 허용
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/views/login") //로그인페이지를 안써도 넣어줄 것
                        .successHandler(oAuth2SuccessHandler)
                        .redirectionEndpoint(redirection -> redirection.baseUri("/auth/oauth/**")) // 리다이렉트 URL 직접 설정
                        .userInfoEndpoint(userInfo -> {
                            userInfo.userService(oAuth2UserService);
                        })
                ).sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                ).addFilterBefore(
                        jwtTokenFilter,
                        AuthorizationFilter.class
                );

        return http.build();

    }
}