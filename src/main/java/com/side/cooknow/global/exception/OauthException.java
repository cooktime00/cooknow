package com.side.cooknow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthException extends RuntimeException{
    OauthErrorCode errorCode;
}
