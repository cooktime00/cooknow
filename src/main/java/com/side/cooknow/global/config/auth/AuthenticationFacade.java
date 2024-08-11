package com.side.cooknow.global.config.auth;

import com.side.cooknow.domain.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((OauthAuthenticationToken) authentication).getUser();
    }
}
