package com.zerobase.zbfintech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 회원이 없습니다."),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST,"이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST,"잘못된 인증 시도입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST,"인증 시간이 만료 되었습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),

    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해 주세요"),
    AES_ENCRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AES 암호화에 실패했습니다."),
    AES_DECRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AES 복호화에 실패했습니다.");

    ;

    private final HttpStatus httpStatus;
    private final String description;

    ErrorCode(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
