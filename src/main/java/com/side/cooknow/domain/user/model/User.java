package com.side.cooknow.domain.user.model;

import com.side.cooknow.global.model.BaseEntity;
import com.side.cooknow.domain.useritem.model.UserItems;
import jakarta.persistence.*;
import lombok.Getter;

import static java.time.LocalDateTime.now;

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
    }

    // 생성자 체이닝을 사용하여 중복 제거
    public User(final Long userId, final String email) {
        super(userId);
        this.email = new Email(email);
    }

    public User(final String email) {
        this.email = new Email(email);
    }

    public User(final String provider, final String email, final String firstName, final String lastName, final Role role) {
        this(null, provider, email, firstName, lastName, role);
    }

    public User(final Long id, final String provider, final String email, final String firstName, final String lastName, final Role role) {
        super(id);
        this.provider = provider;
        this.email = new Email(email);
        this.fullName = new FullName(firstName, lastName);
        this.role = role;
    }

    public boolean isValidateRequest(final String email){
        return this.email.sameValueAs(email);
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

}
