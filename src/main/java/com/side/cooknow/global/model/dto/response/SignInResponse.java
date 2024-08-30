package com.side.cooknow.global.model.dto.response;

import com.side.cooknow.global.model.security.RefreshToken;
import lombok.Data;

@Data
public class SignInResponse {
    private Long id;
    private String accessToken;
    private String refreshToken;

    public SignInResponse(long id, String accessToken, RefreshToken refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getTokenValue();
    }

}
