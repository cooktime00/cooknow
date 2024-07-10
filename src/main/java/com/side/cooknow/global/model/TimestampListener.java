package com.side.cooknow.global.model;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class TimestampListener {

    @PrePersist
    public void setCreatedDate(Object target) {
        if (target instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) target;
            if(entity.getTimestamp() == null) {
                entity.create();
            }
        }
    }

    @PreUpdate
    public void setLastModifiedDate(Object target) {
        if (target instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) target;
            entity.update();
        }
    }
}
