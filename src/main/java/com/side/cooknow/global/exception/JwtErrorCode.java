package com.side.cooknow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements ErrorCode {

    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT-001", "The JWT signature is invalid."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT-002", "The JWT token is expired."),
    TOKEN_INACTIVE(HttpStatus.UNAUTHORIZED, "JWT-003", "The JWT token is not yet active."),
    REPLAY_ATTACK_DETECTED(HttpStatus.FORBIDDEN, "JWT-004", "Replay attack detected for the JWT token."),
    INVALID_CLAIMS(HttpStatus.BAD_REQUEST, "JWT-005", "The JWT token contains invalid claims."),
    UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "JWT-006", "The JWT token is signed with an unsupported algorithm."),
    FORMAT_ERROR(HttpStatus.BAD_REQUEST, "JWT-007", "The JWT token format is invalid."),
    INVALID_KEY(HttpStatus.UNAUTHORIZED, "JWT-008", "The key used for JWT verification is invalid."),
    ENCRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JWT-009", "Error occurred during JWT encryption or decryption."),
    INVALID_AUDIENCE(HttpStatus.FORBIDDEN, "JWT-010", "The audience claim ('aud') is invalid."),
    INVALID_ISSUER(HttpStatus.FORBIDDEN, "JWT-011", "The issuer claim ('iss') is invalid."),
    UNKNOWN(HttpStatus.BAD_REQUEST, "FIREBASE-000", "Unknown error.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private static final Map<String, JwtErrorCode> ERROR_CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(JwtErrorCode::getName, Function.identity())));



    @Override
    public String getName() {
        return "";
    }
}
