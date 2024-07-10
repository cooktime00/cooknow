package com.side.cooknow.global.model.dto.response;

import lombok.Data;

@Data
public class ResponseRefresh {

    private String accessToken;

    public ResponseRefresh(String accessToken) {
        this.accessToken = accessToken;
    }
}
