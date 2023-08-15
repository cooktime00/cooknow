package com.side.cooktime.domain.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FullName {

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50)
    private String lastName;

    protected FullName(final String firstName, final String lastName) {
        validate(firstName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validate(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("이름(FirstName)이 공백이거나 Null 값입니다.");
        }
    }

    @Override
    public String toString() {
        return lastName + firstName;
    }
}
