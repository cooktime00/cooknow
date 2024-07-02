package com.side.cooktime.domain.useritem.model.dto.response;

import lombok.Getter;

@Getter
public class ResponseSaveDto {

    private int size;

    public ResponseSaveDto(int size) {
        this.size = size;
    }
}
