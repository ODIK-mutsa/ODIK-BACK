package com.micutne.odik.common.exception;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
