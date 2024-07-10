package com.side.cooknow.domain.useritem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserItemException extends RuntimeException{
    UserItemErrorCode errorCode;
}
