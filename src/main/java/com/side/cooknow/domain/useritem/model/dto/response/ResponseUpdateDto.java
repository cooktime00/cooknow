package com.side.cooknow.domain.useritem.model.dto.response;

import lombok.Getter;

@Getter
public class ResponseUpdateDto {

    private final int size;

    public ResponseUpdateDto(final int size) {
        this.size = size;
    }
}
