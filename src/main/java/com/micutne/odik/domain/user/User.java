package com.micutne.odik.domain.user;

import com.micutne.odik.domain.user.dto.SignUpRequest;
import com.micutne.odik.domain.user.dto.UserRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 128, unique = true)
    private String id;
    @Column(length = 6, nullable = false)
    private String loginType;
    @Column(length = 20)
    private String nickName;
    @Column(length = 1)
    private String gender;
    @Column(length = 2)
    private String locale;
    @CreatedDate
    @Column(updatable = false)
    private Instant dateJoin;
    @Column(length = 8, nullable = false)
    private String state;

    public static User fromDto(SignUpRequest request) {
        User user = new User();
        user.id = request.getId();
        user.loginType = request.getLoginType();
        user.nickName = request.getNickName();
        user.gender = request.getGender();
        user.state = request.getState();
        user.locale = request.getLocale();
        return user;
    }


    public void updateInfo(UserRequest userRequest) {
        this.nickName = userRequest.getNickName();
        this.gender = userRequest.getGender();
    }

    public void updateState(UserRequest userRequest) {
        this.state = userRequest.getState();
    }
}
