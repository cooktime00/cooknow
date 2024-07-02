package com.side.cooktime.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Transient
    private static final int MINIMUM_LENGTH = 8;

    @Column(name = "password", nullable = true)
    private String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호(Password)가 공백이거나 Null 값입니다.");
        }
        if (password.length() < MINIMUM_LENGTH) {
            throw new IllegalArgumentException("비밀번호(Password)가 " + MINIMUM_LENGTH + "자 미만 값입니다.");
        }
    }

}
