package com.side.cooknow.global.model.security;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class Token {

    @Column(name = "token", nullable = false)
    private String token;

    public Token(){
        this.token = UUID.randomUUID().toString();
    }

    public Token(final String token) {
        this.token = token;
    }

    public static Token createInstance(){
        return new Token();
    }

    public static Token of(final String token) {
        return new Token(token);
    }

}
