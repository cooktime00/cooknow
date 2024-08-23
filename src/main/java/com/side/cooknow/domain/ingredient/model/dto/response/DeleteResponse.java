package com.side.cooknow.domain.ingredient.model.dto.response;

import lombok.Data;

@Data
public class DeleteResponse {

    private Long id;

    public DeleteResponse(Long id){
        this.id = id;
    }
}
