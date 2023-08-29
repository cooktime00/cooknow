package com.side.cooktime.domain.member.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberTest {

    @Test
    public void user_생성() {
        UserGoogleInfo userGoogleInfo = new UserGoogleInfo("1548653124865123", "picture", "kr");
        Member member = new User(userGoogleInfo, "user@gmail.com", "길동", "홍");
        assertAll(
                () -> assertThat(member instanceof User).isTrue(),
                () -> assertThat(member instanceof Admin).isFalse()
        );
    }

    @Test
    public void admin_생성() {
        Member member = new Admin("admin@gmail.com", "password1234", "길동", "홍");
        assertAll(
                () -> assertThat(member instanceof User).isFalse(),
                () -> assertThat(member instanceof Admin).isTrue()
        );
    }

    @ParameterizedTest(name = "email 입력값: {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"user", "user@"})
    void email_유효성검사(String email) {
        UserGoogleInfo userGoogleInfo = new UserGoogleInfo("1548653124865123", "picture", "kr");
        assertThatThrownBy(() -> new User(userGoogleInfo, email, "길동", "홍")).isInstanceOf(IllegalArgumentException.class);
    }
}
