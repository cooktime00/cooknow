package com.side.cooknow.global.model.dto.response;

import com.side.cooknow.global.model.security.RefreshToken;
import lombok.Data;

@Data
public class ResponseSignIn {

    String accessToken;
    String refreshToken;

    public ResponseSignIn(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getTokenValue();
    }

}
