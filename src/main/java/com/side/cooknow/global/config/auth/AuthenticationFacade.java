package com.side.cooknow.global.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof FirebaseAuthenticationToken) {
            return ((FirebaseAuthenticationToken) authentication).getEmail();
        }

        if(authentication instanceof OauthAuthenticationToken){
            return ((OauthAuthenticationToken) authentication).getEmail();
        }
        return null;
    }

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OauthAuthenticationToken) {
            return ((OauthAuthenticationToken) authentication).getId();
        }
        return null;
    }
}
