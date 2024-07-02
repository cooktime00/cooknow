package com.side.cooktime.global.config.auth;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class OauthAuthenticationToken extends AbstractAuthenticationToken {

    private final Long id;
    private final String email;

    public OauthAuthenticationToken(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id = id;
        this.email = email;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }
}
