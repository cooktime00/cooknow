package com.side.cooktime.domain.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {

    @Column(name = "age")
    private int age;
    public Age(final int age) {
        validate(age);
        this.age = age;
    }

    private void validate(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("나이(Age)가 0 미만 값입니다.");
        }
    }
}
