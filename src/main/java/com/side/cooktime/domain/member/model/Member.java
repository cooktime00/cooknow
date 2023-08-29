package com.side.cooktime.domain.member.model;

import com.side.cooktime.global.model.BaseEntity;
import com.side.cooktime.domain.userstorage.model.UserStorages;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_type")
public abstract class Member extends BaseEntity {

    @Embedded
    protected Email email;
    @Embedded
    protected Password password;
    @Embedded
    protected FullName fullName;
    @Embedded
    private final UserStorages userStorages = new UserStorages();

    @Transient
    protected Role role;

    @PostLoad
    private void postLoad() {
        if (this instanceof User) {
            role = Role.USER;
        } else if (this instanceof Admin) {
            role = Role.ADMIN;
        }
    }

    protected Member(final Role role, final String email, final String password, final String firstName, final String lastName) {
        super();
        this.role = role;
        this.email = new Email(email);
        this.password = new Password(password);
        this.fullName = new FullName(firstName, lastName);
    }

    public String getRoleKey() {
        return role.getKey();
    }

    protected Member() {
        ;
    }

    public String getEmailValue() {
        return email.getEmail();
    }

    public String getFullName() {
        return fullName.toString();
    }
}
