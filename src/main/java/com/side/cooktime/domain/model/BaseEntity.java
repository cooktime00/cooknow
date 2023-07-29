package com.side.cooktime.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EntityListeners(TimestampListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Embedded
    protected Timestamp timestamp;

    protected BaseEntity() {
        this.timestamp = new Timestamp();
    }

    protected void create(){
        timestamp = new Timestamp();
    }
    protected void update() {
        timestamp = timestamp.update();
    }
}
