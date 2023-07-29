package com.side.cooktime.domain.member.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
//@DiscriminatorValue("A")
public class Admin extends Member {

    public Admin(final String email, final String password, final String firstName, final String lastName) {
        super(email, password, firstName, lastName);
    }

    protected Admin() {
        ;
    }
}
