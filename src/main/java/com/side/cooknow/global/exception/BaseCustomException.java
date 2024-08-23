package com.side.cooknow.global.exception;

import lombok.Getter;

@Getter
public abstract class BaseCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseCustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
