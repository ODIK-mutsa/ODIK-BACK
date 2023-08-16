package com.micutne.odik.domain.user;

import com.micutne.odik.domain.user.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30, unique = true)
    private String id;
    @Column(length = 6, nullable = false)
    private String loginType;
    @Column(length = 20)
    private String nickName;
    @Column(length = 1)
    private String gender;
    @CreatedDate
    @Column(updatable = false)
    private Instant dateJoin;
    @Column(length = 4, nullable = false)
    private String state;

    public static User fromDto(SignUpRequest request) {
        User user = new User();
        user.id = request.getId();
        user.loginType = request.getLoginType();
        user.nickName = request.getNickName();
        user.gender = request.getGender();
        user.state = request.getState();
        return user;
    }

}
