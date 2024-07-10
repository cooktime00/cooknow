package com.side.cooknow.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{
    UserErrorCode errorCode;
}
