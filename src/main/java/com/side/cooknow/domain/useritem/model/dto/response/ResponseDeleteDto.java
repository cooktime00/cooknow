package com.side.cooknow.domain.useritem.model.dto.response;

import lombok.Getter;

@Getter
public class ResponseDeleteDto {

    private int size;

    public ResponseDeleteDto(final int size) {
        this.size = size;
    }
}
