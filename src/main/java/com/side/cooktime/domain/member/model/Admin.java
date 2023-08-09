package com.side.cooktime.domain.member.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Admin extends Member {

    public Admin(final String email, final String password, final String firstName, final String lastName) {
        super(Role.ADMIN, email, password, firstName, lastName);
    }

    protected Admin() {
        ;
    }
}
