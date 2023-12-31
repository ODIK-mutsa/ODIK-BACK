package com.micutne.odik.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "US_001", "회원을 찾을 수 없습니다."),
    USER_EMAIL_USED(HttpStatus.BAD_REQUEST, "US_002", "이미 사용중인 이메일 입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "US_003", "이미 존재하는 아이디 입니다."),
    USER_PRIVATE_NOT_FOUND(HttpStatus.NOT_FOUND, "US_004", "회원 정보를 찾을 수 없습니다."),

    AUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "AT_001", "인증을 찾을 수 없습니다."),
    AUTH_PASSWORD_UNEQUAL(HttpStatus.NOT_FOUND, "AT_002", "비밀번호가 일치하지 않습니다."),
    AUTH_REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "AT_003", "리프레쉬 토큰을 찾을 수 없습니다."),
    AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "AT_004", "리프레쉬 토큰 인증에 실패했습니다."),
    AUTH_EMAIL_VERIFY_NOT_FOUND(HttpStatus.BAD_REQUEST, "AT_005", "해당 이메일의 이메일 인증 정보가 존재하지 않습니다."),

    TOUR_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "TI_001", "장소를 찾을 수 없습니다."),
    TOUR_ITEM_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "TI_002", "장소를 삭제 중 오류가 발생하였습니다."),
    TOUR_ITEM_ALREADY_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "TI_003", "이미 존재하는 장소입니다."),

    REVIEW_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "RC_001", "해당 댓글을 찾을 수 없습니다."),

    REVIEW_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "RI_001", "해당 댓글을 찾을 수 없습니다."),
    REVIEW_ITEM_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "RI_002", "리뷰를 삭제 중 오류가 발생하였습니다."),

    IMAGE_TOUR_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "II_001", "이미지를 찾을 수 없습니다."),

    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FL_OO1", "파일 업로드 중 오류가 발생하였습니다."),
    FILE_READING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FL_OO2", "파일 읽기 중 오류가 발생하였습니다."),
    FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FL_OO3", "파일 저장 중 오류가 발생하였습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F_004", "파일을 찾을 수 없습니다."),
    FILE_SIZE_ZERO(HttpStatus.BAD_REQUEST, "F_005", "파일 크기가 0입니다."),
    FILE_NOT_VALID_EXTENSION(HttpStatus.BAD_REQUEST, "F_006", "허용되지 않은 확장자 입니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "S_002", "잘못된 요청 값입니다."),

    INVALID_EMAIL_VALUE(HttpStatus.BAD_REQUEST, "EM_001", "존재하지 않는 이메일입니다."),
    INVALID_EMAIL_EXIST(HttpStatus.BAD_REQUEST, "EM_002", "이미 존재하는 이메일입니다."),
    EMAIL_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "EM_003", "이메일 인증 토큰이 일치하지 않습니다."),
    INVALID_PW_VALUE(HttpStatus.BAD_REQUEST, "PW_001", "비밀번호가 맞지 않습니다."),
    NOT_YOURS(HttpStatus.BAD_REQUEST, "PW_000", "인증에 실패했습니다."),
    TOUR_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "TC_001", "코스를 찾을 수 없습니다."),
    HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "H_001", "좋아요 기록을 찾을 수 없습니다."),
    SEARCH_KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "S_001", "검색어 기록을 찾을 수 없습니다.");


    //------------------------------------------------------------------------//
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}