package com.side.cooktime.domain.useritem.exception;


import com.side.cooktime.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum UserStorageErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    HAS_EMAIL(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
