package com.side.cooktime.config.auth;

import com.side.cooktime.domain.member.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUser implements Serializable {
    private Long id;
    private String email;
    private String name;

    public SessionUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail().getEmail();
        this.name = user.getName().toString();
    }

    public SessionUser(String email, String given_name, String family_name) {
        this.email = email;
        this.name = family_name + given_name;
    }
}
