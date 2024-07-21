package com.side.cooknow.domain.user.model;

import com.side.cooknow.global.model.BaseEntity;
import com.side.cooknow.domain.useritem.model.UserItems;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String provider;
    @Embedded
    private Email email;
    @Embedded
    private FullName fullName;
    @Column(columnDefinition = "ENUM('ADMIN', 'USER') default 'USER'", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private final UserItems userItems = new UserItems();

    public User() {
        ;
    }
    public User(final Long id, final String provider, final String email, final String firstName, final String lastName, final Role role) {
        super(id);
        this.provider = provider;
        this.role = role;
        this.email = new Email(email);
        this.fullName = new FullName(firstName, lastName);
    }

    public User(final String provider, final String email, final String firstName, final String lastName, final Role role) {
        super();
        this.provider = provider;
        this.role = role;
        this.email = new Email(email);
        this.fullName = new FullName(firstName, lastName);
    }

    public String getRoleKey() {
        return role.getKey();
    }

    public String getEmailValue() {
        return email.getEmail();
    }

    public String getFullName() {
        return fullName.toString();
    }

    public String getRole() {
        return role.getKey();
    }
}
