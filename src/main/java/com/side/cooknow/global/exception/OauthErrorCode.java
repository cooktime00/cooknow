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
public enum OauthErrorCode implements ErrorCode {

    UNKNOWN(HttpStatus.BAD_REQUEST, "OAUTH-000", "Unknown error."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "OAUTH-001", "존재하지 않은 Refresh Token 입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-002", "해당 Refresh Token은 만료되었습니다."),

    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-003", "해당 Access Token은 유효하지 않습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "OAUTH-004", "The JWT signature is invalid."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-005", "The JWT token is expired."),
    TOKEN_INACTIVE(HttpStatus.UNAUTHORIZED, "OAUTH-006", "The JWT token is not yet active."),
    REPLAY_ATTACK_DETECTED(HttpStatus.FORBIDDEN, "OAUTH-007", "Replay attack detected for the JWT token."),
    INVALID_CLAIMS(HttpStatus.BAD_REQUEST, "OAUTH-008", "The JWT token contains invalid claims."),
    UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "OAUTH-006", "The JWT token is signed with an unsupported algorithm."),
    FORMAT_ERROR(HttpStatus.BAD_REQUEST, "OAUTH-007", "The JWT token format is invalid."),
    INVALID_KEY(HttpStatus.UNAUTHORIZED, "OAUTH-008", "The key used for JWT verification is invalid."),
    ENCRYPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH-009", "Error occurred during JWT encryption or decryption."),
    INVALID_AUDIENCE(HttpStatus.FORBIDDEN, "OAUTH-010", "The audience claim ('aud') is invalid."),
    INVALID_ISSUER(HttpStatus.FORBIDDEN, "OAUTH-011", "The issuer claim ('iss') is invalid."),
    CERTIFICATE_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH-012", "Failed to retrieve public key certificates required to verify JWTs."),
    CONFIGURATION_NOT_FOUND(HttpStatus.NOT_FOUND, "OAUTH-013", "No IdP configuration found for the given identifier."),
    UNKNOWN_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-015", "The specified access token is unknown."),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "OAUTH-014", "A user already exists with the provided email."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "OAUTH-015", "No user record found for the given email."),
    EXPIRED_ID_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-016", "The specified ID token is expired."),
    EXPIRED_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "OAUTH-017", "The specified session cookie is expired."),
    INVALID_DYNAMIC_LINK_DOMAIN(HttpStatus.BAD_REQUEST, "OAUTH-018", "The provided dynamic link domain is not configured or authorized for the current project."),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-019", "The specified ID token is invalid."),
    INVALID_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "OAUTH-020", "The specified session cookie is invalid."),
    PHONE_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "OAUTH-021", "A user already exists with the provided phone number."),
    REVOKED_ID_TOKEN(HttpStatus.UNAUTHORIZED, "OAUTH-022", "The specified ID token has been revoked."),
    REVOKED_SESSION_COOKIE(HttpStatus.UNAUTHORIZED, "OAUTH-023", "The specified session cookie has been revoked."),
    TENANT_ID_MISMATCH(HttpStatus.BAD_REQUEST, "OAUTH-024", "Tenant ID in the JWT does not match."),
    TENANT_NOT_FOUND(HttpStatus.NOT_FOUND, "OAUTH-025", "No tenant found for the given identifier."),
    UID_ALREADY_EXISTS(HttpStatus.CONFLICT, "OAUTH-026", "A user already exists with the provided UID."),
    UNAUTHORIZED_CONTINUE_URL(HttpStatus.FORBIDDEN, "OAUTH-027", "The domain of the continue URL is not whitelisted."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAUTH-028", "No user record found for the given identifier."),
    USER_DISABLED(HttpStatus.FORBIDDEN, "OAUTH-029", "The user record is disabled.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private static final Map<String, OauthErrorCode> ERROR_CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(OauthErrorCode::getName, Function.identity())));

    public static OauthErrorCode fromName(String code) {
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
