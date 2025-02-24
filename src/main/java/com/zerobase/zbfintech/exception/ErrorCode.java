package com.zerobase.zbfintech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 회원이 없습니다.");

    private final HttpStatus httpStatus;
    private final String description;

    ErrorCode(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
