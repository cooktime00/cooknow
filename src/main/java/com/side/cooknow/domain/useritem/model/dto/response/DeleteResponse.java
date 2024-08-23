package com.side.cooknow.domain.useritem.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteResponse {

    private List<Long> ids;

    public DeleteResponse(final List<Long> ids) {
        this.ids = ids;
    }
}
