package com.side.cooktime.domain.category.model.dto.response;

import lombok.Data;

@Data
public class ResponseDeleteDto {

    private Long id;

    public ResponseDeleteDto(Long id){
        this.id = id;
    }
}
