package com.side.cooktime.domain.ingredient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class Image {

    @Column(length = 100)
    private String url;

    public Image(final String url) {
        this.url = url;
    }
}
