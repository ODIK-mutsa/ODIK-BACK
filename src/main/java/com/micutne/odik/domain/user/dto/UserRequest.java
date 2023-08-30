package com.micutne.odik.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    String nick_name;
    String gender;
    String state;
    String locale;


}
