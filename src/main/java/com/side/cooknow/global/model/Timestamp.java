package com.side.cooknow.global.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class Timestamp {

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Timestamp() {
        this(LocalDateTime.now(), LocalDateTime.now());
    }

    public Timestamp(LocalDateTime createdAt) {
        this(createdAt, LocalDateTime.now());
    }

    public Timestamp(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Timestamp update() {
        return new Timestamp(this.createdAt);
    }
}
