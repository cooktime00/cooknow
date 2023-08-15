package com.side.cooktime.domain.category.model.dto.response;

import lombok.Data;

@Data
public class ResponseSaveDto {

    private int size;

    public ResponseSaveDto(int size) {
        this.size = size;
    }
}
