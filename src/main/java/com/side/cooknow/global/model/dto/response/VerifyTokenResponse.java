package com.side.cooknow.global.model.dto.response;

import lombok.Data;

@Data
public class VerifyTokenResponse {
    private String result = "Token is valid";

    public VerifyTokenResponse() {

    }
}
