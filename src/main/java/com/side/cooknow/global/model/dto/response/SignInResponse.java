package com.side.cooknow.global.model.dto.response;

import com.side.cooknow.global.model.security.RefreshToken;
import lombok.Data;

@Data
public class SignInResponse {

    String accessToken;
    String refreshToken;

    public SignInResponse(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getTokenValue();
    }

}
