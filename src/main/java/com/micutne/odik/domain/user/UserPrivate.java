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
    //TODO : 82-10-1234-5678 형식의 전화번호는 17 필요, ERD 형식은 12
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
