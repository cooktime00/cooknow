package com.side.cooktime.domain.ingredient.model.dto.response;

import lombok.Data;

@Data
public class ResponseDeleteDto {

    private Long id;

    public ResponseDeleteDto(Long id){
        this.id = id;
    }
}
