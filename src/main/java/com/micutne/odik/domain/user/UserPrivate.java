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
    private Long idx;
    @Column(length = 17)
    private String phone;
    @Column(length = 512)
    private String pss;
    @Column(length = 512)
    private String sort;

    public static UserPrivate fromDto(SignUpRequest request, User user) {
        UserPrivate userPrivate = new UserPrivate();
        userPrivate.idx = user.getIdx();
        userPrivate.phone = request.getPhone();
        userPrivate.pss = request.getPss();
        userPrivate.sort = request.getSort();
        return userPrivate;
    }
}
