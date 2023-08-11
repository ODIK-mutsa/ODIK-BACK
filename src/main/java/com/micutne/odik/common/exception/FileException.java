package com.micutne.odik.common.exception;

public class FileException extends BusinessException {
    public FileException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
