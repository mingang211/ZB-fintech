package com.zerobase.zbfintech.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    public final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
