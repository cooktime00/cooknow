package com.side.cooktime.domain.member.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
//@DiscriminatorValue("U")
public class User extends Member {

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(final String provider, final String providerId, final String email, final String password, final String firstName, final String lastName, final String gender, final int age) {
        super(provider, providerId, email, password, firstName, lastName);
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.age = new Age(age);
        this.role = Role.USER;
    }

    public User(final String email, final String password, final String firstName, final String lastName, final Gender gender, final int age) {
        super(email, password, firstName, lastName);
        this.gender = gender;
        this.age = new Age(age);
        this.role = Role.USER;
    }

    protected User() {
        ;
    }

    public String getRoleKey() {
        return role.getKey();
    }
}
