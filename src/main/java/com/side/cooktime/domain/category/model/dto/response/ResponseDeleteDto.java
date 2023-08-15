package com.side.cooktime.domain.category.model.dto.response;

import lombok.Data;

@Data
public class ResponseDeleteDto {

    private int size;

    public ResponseDeleteDto(final int size){
        this.size = size;
    }
}
