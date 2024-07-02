package com.side.cooktime.domain.user.model;

import lombok.Getter;

@Getter
public enum Provider {
    GOOGLE("google");

    private final String value;

    Provider(String value) {
        this.value = value;
    }
}
