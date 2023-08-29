package com.side.cooktime.config.auth;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String token;
    private final String memberName;
    private final boolean autoLogin;
    private final boolean notiPermission;
    private final boolean adPermission;

    public LoginResponse(String token, String memberName, boolean autoLogin, boolean notiPermission, boolean adPermission) {
        this.token = token;
        this.memberName = memberName;
        this.autoLogin = autoLogin;
        this.notiPermission = notiPermission;
        this.adPermission = adPermission;
    }
}
