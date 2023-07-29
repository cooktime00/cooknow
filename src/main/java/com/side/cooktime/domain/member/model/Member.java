package com.side.cooktime.domain.member.model;

import com.side.cooktime.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//TODO: Role을 enum타입 대신 쓸 수 있다면
//@DiscriminatorColumn(name = "role")
public abstract class Member extends BaseEntity {

    @Embedded
    protected Email email;
    @Embedded
    protected Password password;
    @Embedded
    protected Name name;

    protected String provider;
    protected String providerId;

    protected Member(final String email, final String password, final String firstName, final String lastName) {
        super();
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(firstName, lastName);
    }

    protected Member(final String provider, final String providerId, final String email, final String password, final String firstName, final String lastName) {
        super();
        this.provider = provider;
        this.providerId = providerId;
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(firstName, lastName);
    }

    protected Member() {
        ;
    }
}
