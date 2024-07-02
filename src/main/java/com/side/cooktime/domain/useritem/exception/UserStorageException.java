package com.side.cooktime.domain.useritem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserStorageException extends RuntimeException{
    UserStorageErrorCode errorCode;
}
