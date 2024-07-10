package com.side.cooknow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FirebaseAuthException extends RuntimeException{
    FirebaseAuthErrorCode errorCode;
}
