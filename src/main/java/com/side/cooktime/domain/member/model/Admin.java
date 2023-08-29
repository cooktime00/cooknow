package com.side.cooktime.domain.member.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Member {

    public Admin(final String email, final String password, final String firstName, final String lastName) {
        super(Role.ADMIN, email, password, firstName, lastName);
    }

    protected Admin() {
        ;
    }
}
