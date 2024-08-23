package com.side.cooknow.domain.category.model.dto.response;

import lombok.Data;

@Data
public class DeleteResponse {

    private int size;

    public DeleteResponse(final int size){
        this.size = size;
    }
}
