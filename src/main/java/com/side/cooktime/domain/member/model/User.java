package com.side.cooktime.domain.member.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User extends Member {

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Embedded
    private Age age;

    public User(final String provider, final String providerId, final String email, final String password, final String firstName, final String lastName, final String gender, final int age) {
        super(Role.USER, provider, providerId, email, password, firstName, lastName);
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.age = new Age(age);
    }

    public User(final String email, final String password, final String firstName, final String lastName, final Gender gender, final int age) {
        super(Role.USER, email, password, firstName, lastName);
        this.gender = gender;
        this.age = new Age(age);
    }

    protected User() {
        ;
    }
}
