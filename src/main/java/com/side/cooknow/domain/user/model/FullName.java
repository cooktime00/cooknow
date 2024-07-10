package com.side.cooknow.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FullName {

    @Transient
    private static final String KOREAN_REGEX = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";

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
        if(lastName == null || lastName.trim().isEmpty()){
            return firstName;
        }
        if(firstName.matches(KOREAN_REGEX)) {
            return lastName + firstName;
        }
        return firstName + " " + lastName;
    }
}
