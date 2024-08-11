package com.side.cooknow.global.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    @Column(nullable = true)
    protected LocalDateTime deletedAt;

    protected BaseEntity(final Long id){
        this.id = id;
        this.timestamp = new Timestamp();
    }

    protected BaseEntity() {
        this.timestamp = new Timestamp();
    }

    protected void create(){
        timestamp = new Timestamp();
    }
    protected void update() {
        timestamp = timestamp.update();
    }
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
