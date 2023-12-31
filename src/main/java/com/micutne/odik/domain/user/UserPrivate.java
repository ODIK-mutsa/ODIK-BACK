package com.micutne.odik.domain.user;

import com.micutne.odik.domain.user.dto.SignUpRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class UserPrivate {
    @Id
    @Column(name = "user_idx")
    private int idx;
    @Column(length = 17)
    private String phone;
    @Column(length = 512)
    private String pss;

    public static UserPrivate fromDto(SignUpRequest request, User user) {
        UserPrivate userPrivate = new UserPrivate();
        userPrivate.idx = user.getIdx();
        userPrivate.phone = request.getPhone();
        userPrivate.pss = request.getPss();
        return userPrivate;
    }

    public void updatePassword(String newPassword) {
        pss = newPassword;
    }
}
