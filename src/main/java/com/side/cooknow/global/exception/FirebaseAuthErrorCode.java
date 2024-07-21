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
public enum FirebaseAuthErrorCode implements ErrorCode {

    CERTIFICATE_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE-001", "Failed to retrieve public key certificates required to verify JWTs."),
    CONFIGURATION_NOT_FOUND(HttpStatus.NOT_FOUND, "FIREBASE-002", "No IdP configuration found for the given identifier."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "FIREBASE-003", "A user already exists with the provided email."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "FIREBASE-004", "No user record found for the given email."),
    EXPIRED_ID_TOKEN(HttpStatus.UNAUTHORIZED, "FIREBASE-005", "The specified ID token is expired."),
    EXPIRED_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "FIREBASE-006", "The specified session cookie is expired."),
    INVALID_DYNAMIC_LINK_DOMAIN(HttpStatus.BAD_REQUEST, "FIREBASE-007", "The provided dynamic link domain is not configured or authorized for the current project."),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "FIREBASE-008", "The specified ID token is invalid."),
    INVALID_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "FIREBASE-009", "The specified session cookie is invalid."),
    PHONE_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "FIREBASE-010", "A user already exists with the provided phone number."),
    REVOKED_ID_TOKEN(HttpStatus.UNAUTHORIZED, "FIREBASE-011", "The specified ID token has been revoked."),
    REVOKED_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "FIREBASE-012", "The specified session cookie has been revoked."),
    TENANT_ID_MISMATCH(HttpStatus.BAD_REQUEST, "FIREBASE-013", "Tenant ID in the JWT does not match."),
    TENANT_NOT_FOUND(HttpStatus.NOT_FOUND, "FIREBASE-014", "No tenant found for the given identifier."),
    UID_ALREADY_EXISTS(HttpStatus.CONFLICT, "FIREBASE-015", "A user already exists with the provided UID."),
    UNAUTHORIZED_CONTINUE_URL(HttpStatus.FORBIDDEN, "FIREBASE-016", "The domain of the continue URL is not whitelisted."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "FIREBASE-017", "No user record found for the given identifier."),
    USER_DISABLED(HttpStatus.FORBIDDEN, "FIREBASE-018", "The user record is disabled."),
    UNKNOWN(HttpStatus.BAD_REQUEST, "FIREBASE-000", "Unknown error.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private static final Map<String, FirebaseAuthErrorCode> ERROR_CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(FirebaseAuthErrorCode::getName, Function.identity())));


    public static FirebaseAuthErrorCode fromName(String code) {
        if (ERROR_CODE_MAP.containsKey(code)) {
            return ERROR_CODE_MAP.get(code);
        }
        return UNKNOWN;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
