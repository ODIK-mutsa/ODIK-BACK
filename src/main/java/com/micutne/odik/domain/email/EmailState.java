package com.micutne.odik.domain.email;

import lombok.Getter;

@Getter
public enum EmailState {
    SEND,
    ALREADY_EXIST,
    OK,
    WRONG_CODE,
    EXPIRED, NOT_EXIST;

    EmailState() {

    }
}
